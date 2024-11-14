package com.example.boardproj.controller;


import com.example.boardproj.dto.*;
import com.example.boardproj.entity.Reply;
import com.example.boardproj.repository.ReplyRepository;
import com.example.boardproj.service.BoardImgService;
import com.example.boardproj.service.BoardService;
import com.example.boardproj.service.ReplyService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final BoardImgService boardImgService;
    private final ReplyService replyService;

    @GetMapping("/b")
    public @ResponseBody String bb(){

        return "홍길동";
    }

    //등록보기페이지
    @GetMapping("/register")
    public String registerGet(BoardDTO boardDTO){

        return "board/register";
    }
    //글을 디비에 등록하는 포스트
    @PostMapping("register")
    public String registerPost(@Valid BoardDTO boardDTO, BindingResult bindingResult, MultipartFile[] multipartFile){
        log.info("컨트롤러로 들어온값 : " + boardDTO);
//        log.info("컨트롤러로 들어온값 : " + multipartFile.getOriginalFilename());

//        리스트로 작동하는지 확인 (리스트가 에러가 덜난다고 함)
//        if (ass != null) {
//            for (MultipartFile a : ass) {
//                log.info(a.getOriginalFilename());
//            }
//        }


//        if (as != null) {
//            for (MultipartFile a : as) {
//                log.info(a.getOriginalFilename());
//            }
//        }



        if (bindingResult.hasErrors()){     // hasErrors() 참 거짓을 받음
            log.info("유효성 검사간 문제가 있다. 아래 로그는 모든 문제를 출력해준다.");
            log.info(bindingResult.getAllErrors());
            // 유효성 검사간 문제가 있다면 다시 문제의 페이지로 돌려주고
            // 사용자가 문제를 볼 수 있어야한다.
            // model에 값을 할당하지 않아도 가지고 간다.

            return "board/register";
        }



//        boardService.register(boardDTO);
        // 파일 업로드 추가
        boardService.register(boardDTO, multipartFile);


        return "redirect:/board/list";

    }

    @GetMapping("/read")
    public String read(Long bno, Model model, PageRequestDTO pageRequestDTO){
        log.info("컨트롤러 읽기로 들어온 게시글번호 : " +bno);
        log.info("컨트롤러 읽기로 들어온 페이징처리 : " +pageRequestDTO);

        if (bno == null || bno.equals("")){     // 예외처리하기 전 사전에 이 경우의 수를 막음
            log.info("들어온 bno가 이상함");

            return "redirect:/board/list";

        }

        try {

            // 본문 데이터 // 아직 단방향 중
            // 양방향 아님
            BoardDTO boardDTO = boardService.read(bno);     // 글 불러오기

            List<BoardImgDTO> boardImgDTOList =             // 해당 글 이미지 bno로 가져오기
                    boardImgService.boardImgread(bno);

            List<ReplyDTO> replyDTOList =                   // 해당 글 댓글목록 bno로 가져오기
                replyService.list(bno);

            log.info("컨트롤러에서 서비스 read() 불러온값 : " );
            log.info(boardDTO);
            model.addAttribute("boardDTO", boardDTO);               // boardDTO view로 보내주기
            model.addAttribute("boardImgDTOList", boardImgDTOList); // boardImgDTOList view로 보내주기
            model.addAttribute("replyDTOList", replyDTOList);       // replyDTOList view로 보내주기

        } catch (EntityNotFoundException e) {       // 하위 클래스 예외처리가 우선 진행되어야 함
            log.info("bno로 값을 찾지 못함 ");
            log.info("pk값이 bno인 데이터가 없음 ");

            return "redirect:/board/list";
        } catch (Exception e) {                     // 상위 클래스 예외처리가 나중에 진행되어야 함
            log.info("bno를 입력하지 않음. 못받음");
            return "redirect:/board/list";
        }

        return "board/read";
    }
    @GetMapping("/list")
    public String list(Model model , PageRequestDTO pageRequestDTO){

        log.info(pageRequestDTO);

//        if(pageRequestDTO.getType().equals("t")){
//            log.info("제목에서 검색하세요");
//            log.info("키워드는 " + pageRequestDTO.getKeyword());
//        }
//
//        if(pageRequestDTO.getType().equals("c")){
//            log.info("내용에서 검색하세요");
//            log.info("키워드는 " + pageRequestDTO.getKeyword());
//        }

//        List<BoardDTO> boardDTOList =
//        boardService.list();                      // 그냥 게시판목록 전부 가져옴

        PageResponseDTO<BoardDTO> pageResponseDTO =
//        boardService.pagelist(pageRequestDTO);    // 페이징처리만 된 서비스 가져옴
                boardService.pageListSearchDSL(pageRequestDTO);
                // 페이징처리,검색 동적처리 작용한 서비스 가져옴


        model.addAttribute("pageResponseDTO", pageResponseDTO); // pageResponseDTO view로 보내주기

        return "board/list";
    }

    @GetMapping("/list1")
    public String list1(Model model , PageRequestDTO pageRequestDTO){

        log.info(pageRequestDTO);




//        List<BoardDTO> boardDTOList =
//        boardService.list();

        PageResponseDTO<BoardDTO> pageResponseDTO =
                boardService.pagelist(pageRequestDTO);


        model.addAttribute("pageResponseDTO", pageResponseDTO);

        return "board/list1";
    }

    @GetMapping("/update")
    public String update(Long bno, Model model, PageRequestDTO pageRequestDTO){

        if (bno == null || bno.equals("")){     // 예외처리하기 전 사전에 이 경우의 수를 막음
            log.info("들어온 bno가 이상함");

            return "redirect:/board/list" + pageRequestDTO.getLink();   // 페이지 위치를 포함한 리다이렉트

        }

        try {

            BoardDTO boardDTO = boardService.read(bno);     // 해당 bno의 글 가져옴

            List<BoardImgDTO> boardImgDTOList =             // 해당 bno의 글 이미지를 가져옴
                    boardImgService.boardImgread(bno);

            model.addAttribute("boardDTO", boardDTO);               // boardDTO view로 보내주기
            model.addAttribute("boardImgDTOList", boardImgDTOList); // boardImgDTOList view로 보내주기

        }catch (EntityNotFoundException e) {        // 예외처리
            e.printStackTrace();                    // 예외가 발생한 이유 출력
            log.info("pk로 검색한 값이 없음");

            return "redirect:/board/list?" + pageRequestDTO.getLink();  // 페이지 위치를 포함한 리다이렉트
        }

        return "board/update";
    }


    @PostMapping("/update")     // 주의! @Valid 뒤엔 반드시 BindingResult 들어가야함
    public String updatePost(@Valid BoardDTO boardDTO, BindingResult bindingResult, Long[] delino,
                             PageRequestDTO pageRequestDTO, Model model, MultipartFile[] multipartFiles){

        log.info("업데이트포스 " + boardDTO);
        log.info("업데이트포스 " + pageRequestDTO);


        // 유효성 검사 진행
        if (bindingResult.hasErrors()){
            log.info("유효성검사 확인!!");
            log.info(bindingResult.getAllErrors()); // 유효성검사의 모든내용 로그상에 출력

            return "board/update";
        }

        // 예외처리 진행
        try {

            boardService.update(boardDTO, multipartFiles, delino);

            // 이곳에서 진행해도 되고 아예 서비스에 업데이트에서 진행하도록 코드를 짜도 됨
            // 사실 원자성을 위해 트랜잭션을 사용하고 있는 서비스에 클래스에 만들어주는것이 맞음
            // 원자성 : 트랜잭션과 관련된 일은 모두 실행되던지 모두 실행되지 않도록 하던지를 보장하는 특성이다.

            // 서비스로 다 집어넣음
            // 사진 등록
//            if (multipartFile != null && !multipartFile.getOriginalFilename().equals("")) {
//                log.info("여기 사진이 있어요");
//                log.info("업데이트포스 " + multipartFile.getOriginalFilename());
//
//                boardImgService.boardImgregister(boardDTO.getBno(), multipartFile);
//            }
//
//            // 사진 삭제
//            if (delino != null && !delino[0].equals("")) {
//                log.info("업데이트포스 " + Arrays.toString(delino));
//
//                for (Long ino : delino) {
//                    log.info("삭제되었습니다. ");
//
//                    boardImgService.del(ino);
//                }
//
//            }

        }catch (EntityNotFoundException e) {
            // 사용자한테는 글번호가 안보이고 수정 불가능하기 대문에 테스트중에는 사용 X
            // model.addAttribute("msg", "현재 수정하려는 글번호가 올바르지 않습니다.");

            //return "board/update";
            return "redirect:/board/list?" + pageRequestDTO.getLink();
        }

        return "redirect:/board/read?bno=" + boardDTO.getBno() + '&' + pageRequestDTO.getLink();
    }

    @PostMapping("/del")
    public String del(Long bno){

        log.info(bno);

        boardService.del(bno);
        // 현재 댓글이 있는건 자식 테이블에서 참조 하고 있다면
        // 자식이 없어지기 전에는 참조하고 있는 부모테이블의 pk 항목은 지울수 없습니다.
        // 삭제처리를 진행하고 싶다면 repository에서 where구문을 사용해 부모와 자식테이블의 bno값이 같은
        // 것을 찾고 if문을 통해 null인지 같이 있는지 일치하는지등 구분하여 처리진행이 가능하게 해야함

        // 삭제방법이 다양하여 다양하게 생각하고 방향을 정한 후 만들필요 있음
        /* etc.
        1. 게시글 + 댓글의 경우 게시글이 지워지면 댓글이 남는경우
        2. 실제 삭제처리되지않고 del를 Y or N 구분하여 N인 경우에만 출력
        .....
         */

        return "redirect:/board/list";
    }

}
