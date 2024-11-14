package com.example.boardproj.controller;

import com.example.boardproj.dto.PageRequestDTO;
import com.example.boardproj.dto.PageResponseDTO;
import com.example.boardproj.dto.ReplyDTO;
import com.example.boardproj.service.ReplyService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reply")
@Log4j2
public class RestReplyController {
    // 비동기식

    private final ReplyService replyService;

    @PostMapping("/register")
    public ResponseEntity replyregister(@Valid ReplyDTO replyDTO, BindingResult bindingResult){

        log.info("댓글 들어온 값 : " + replyDTO);

       if (bindingResult.hasErrors()) {
           log.info("에러가 있습니다.");
           log.info("에러가 있습니다.");
           log.info("에러가 있습니다.");
           log.info("에러가 있습니다.");
           log.info("에러가 있습니다.");
           log.info(bindingResult.getAllErrors());
           log.info(bindingResult.getAllErrors());

//           List<ObjectError> objectErrors = bindingResult.getAllErrors();
//           log.info("-----------------------------------------------");
//           objectErrors.forEach(objectError -> log.info(objectError));

           // 위 아래 같은 값이 서로 다른 형식으로 출력됨

           // getFieldError는 FieldError타입
//           FieldError fieldError = bindingResult.getFieldError();
//           getFieldErrors는 List<FieldError> 타입
           List<FieldError> fieldErrors = bindingResult.getFieldErrors();
           log.info("-----------------------------------------------");
           fieldErrors.forEach(a -> log.info(a.getDefaultMessage()));   // 에러 기본 메세지만 출력
           log.info("-----------------------------------------------");


           // getAllErrors의 타입은 List<ObjectError>   // read페이지에서 콘솔 확인시 에러목록을 쭉 표시해줌
//           return new ResponseEntity<List<ObjectError>>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
           return new ResponseEntity<String>(fieldErrors.get(0).getDefaultMessage(), HttpStatus.OK);
       }


        if (replyDTO.getBno() == null || replyDTO.getBno().equals("")) {
            return new ResponseEntity<String>("댓글 값들이 안들어옴", HttpStatus.BAD_REQUEST);
        }
        // 댓글 저장
        try {
            log.info("저장 수행");
            replyService.register(replyDTO);

        }catch (EntityNotFoundException e) {
            return new ResponseEntity<String>("입력하신 글에는 댓글이 없습니다.", HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<String>("저장이 되었습니다.", HttpStatus.OK);

    }
/*
    @PostMapping("/list")
    public ResponseEntity replylist(PageRequestDTO pageRequestDTO, Long bno){

        // 댓글을 불러옴
        PageResponseDTO<ReplyDTO> responseDTO =
                replyService.pageList(pageRequestDTO, bno);

        // 만약에 bno가 못들어왔다
        if(bno == null || bno.equals("")) {

            return new ResponseEntity<String>("에러메세지", HttpStatus.BAD_REQUEST);

        }

        return new ResponseEntity<PageResponseDTO<ReplyDTO>>(responseDTO, HttpStatus.OK);

    }
*/
    @GetMapping("/list")
    public ResponseEntity list(Long bno, PageRequestDTO pageRequestDTO) {

        log.info("댓글 리스트" + bno);
        log.info(pageRequestDTO);

        if (bno == null || bno.equals("")) {

            return new ResponseEntity<String>("요청값을 확인해주세요.", HttpStatus.BAD_REQUEST);
        }

        PageResponseDTO<ReplyDTO> responseDTO =
        replyService.pageList(pageRequestDTO, bno);

        return new ResponseEntity<PageResponseDTO<ReplyDTO>>(responseDTO, HttpStatus.OK);

    }

    @GetMapping("/read/{rno}")
    public ResponseEntity read(@PathVariable("rno") Long rno) {

        ReplyDTO replyDTO =
            replyService.read(rno);

        return new ResponseEntity<ReplyDTO>(replyDTO, HttpStatus.OK);

    }

    @PostMapping("/update")
    public ResponseEntity update(ReplyDTO replyDTO) {

        log.info(replyDTO);
        log.info(replyDTO);
        log.info(replyDTO);
        log.info(replyDTO);
        log.info(replyDTO);

        try {
            replyService.update(replyDTO);
        }catch (EntityNotFoundException e) {

            return new ResponseEntity<String>("댓글 못찾음", HttpStatus.BAD_REQUEST);

        }


        return new ResponseEntity<String>("수정됨", HttpStatus.OK);

    }


}
