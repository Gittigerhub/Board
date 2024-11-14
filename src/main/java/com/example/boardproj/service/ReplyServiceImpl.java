package com.example.boardproj.service;

import com.example.boardproj.config.CustomModelMapper;
import com.example.boardproj.dto.BoardDTO;
import com.example.boardproj.dto.PageRequestDTO;
import com.example.boardproj.dto.PageResponseDTO;
import com.example.boardproj.dto.ReplyDTO;
import com.example.boardproj.entity.Board;
import com.example.boardproj.entity.Reply;
import com.example.boardproj.repository.BoardRepository;
import com.example.boardproj.repository.ReplyRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;

//  기존엔 널이라 아래방식으로 했으나
//  private ModelMapper modelMapper = new ModelMapper();
//  bean 설정으로 값이 생겨서 아래와 같이 사용
    private final ModelMapper modelMapper;  // final을 쓰면 bean객체 불러옴
    // 위에는 스프링에 의존성 전달 / 아래는 직접전달, 그렇기 때문에 위 방법 실행
//    private CustomModelMapper customModelMapper = new CustomModelMapper();


    @Override
    public void register(ReplyDTO replyDTO) {
        // 등록   :   댓글은 참조관계로 부모가 Board이기에 Board에서 가지고 있는
        // entity를 set하고 나서 저장해야한다.
        // 일반적으로 Board board = new Board(); 라고 만든
        // board.setBno(replyDTO.getBno)로 만든 객체는 임의의 객체임으로
        // 저장이 되지 않는다.
        Optional<Board> optionalBoard =
            boardRepository.findById(replyDTO.getBno());    // entity를 찾아온다.

        // 없을때는 예외처리
        Board board = optionalBoard.orElseThrow(EntityNotFoundException::new);

        // 엔티티를 찾아오지 않으면 작동 안함 / 그래서 위에 코드작업을 함
        // 부모는 엔티티로 연결되어있고 임의의 객채 dto로는 값이 저장되지 않기 때문
        Reply reply =
            modelMapper.map(replyDTO, Reply.class);
        reply.setBoard(board);

        replyRepository.save(reply);

    }

    @Override
    public ReplyDTO read(Long rno) {

        Reply reply =
            replyRepository.findById(rno).orElseThrow(EntityNotFoundException::new);

        ReplyDTO replyDTO =
                modelMapper.map(reply, ReplyDTO.class);

        return replyDTO;

    }

    @Override
    public List<ReplyDTO> list(Long bno) {

        List<Reply> replyList =
                replyRepository.findByBoardBno(bno);


        List<ReplyDTO> list =
        replyList.stream()
                // 엔티티엔 엔티티 dto엔 dto가 선언되어있어 반환이 안돼 collect가 작동하지않음
                .map( reply -> modelMapper.map(reply, ReplyDTO.class)
                        // 그래서 아래와 같은 방법으로 BoardDTO로 변환하여 값 반환 가능하게 진행
                        .setBoardDTO(modelMapper.map(reply.getBoard(), BoardDTO.class)) )
                            .collect(Collectors.toList());

        // list에서 하나씩 순차적으로 0부터 꺼내서, 꺼낸 아이를 변수 reply라 칭하고
        // 이걸 가지고 replyDTO 타입으로 변환한 다음 리턴값으로 replyDTO 타입으로
        // 받는데 받은 replyDTO에 setBoardDTO를 하기위해 아까 받은 reply에서
        // reply.getBoard를 받아서 다시 BoardDTO 타입으로 변환해서 set하기 위해서
        // setBoardDTO의 리턴타입을 ReplyDTO로 줘서 리턴을 다시 ReplyDTO로 만든다
        // 리턴값이 있어야만 .collect(Collectors.toList());를 사용할 수 있다.

        return list;
    }

    @Override
    public Page<ReplyDTO> listpage(Long bno, Pageable pageable) {
        // pageable은 컨트롤러로부터 현재 페이지를 받아 -1을 줘서
        // 내부적으로 limit을 사용할 수 있도록 값을 주며,
        // 현재 페이지당 보여질 객체수를 결정해 줍니다.
        // 위 2가지만 설정가능하며(오버로딩), 추가로 정렬조건 또한 줄 수 있습니다.
        // 현재 만들어진 pageable을 받았으나 컨트롤러에서 만들어서 넣어주던
        // 아니면 내부적으로 페이지만 받아서 처리하던
        // select * from table order by 정렬조건 limit 현재페이지에서 시작할 번호, 몇개씩
        // select * from table (pageable) 파라미터로 넣어주면 페이지객체로 반환합니다.
        // 15페이지라하면, 10개씩이라하면, 10페이지부터 ~ 20페이지까지 보여줄 예정
        // pageable은 내부에서 직접 만들거 Page와 Size를 받았다면
/*        Pageable ex = PageRequest
                .of((int) (bno-1), 10, Sort.by("rno")
                        .descending());
        Page<Reply> replyPage =
                replyRepository.findByBoardBno(bno, ex);
*/

        Page<Reply> replyPage =
            replyRepository.findByBoardBno(bno, pageable);
        // pageImpl을 이용해서 재지정

        List<Reply> replyList =
                replyPage.getContent();

        List<ReplyDTO> replyDTOList =
            replyList.stream().map(reply -> modelMapper.map(reply, ReplyDTO.class)
                .setBoardDTO(modelMapper.map(reply.getBoard(), BoardDTO.class)))
                .collect(Collectors.toList());

        Page<ReplyDTO> replyDTOPage =
            new PageImpl<ReplyDTO>(replyDTOList , pageable, replyPage.getTotalElements());

        return replyDTOPage;
    }

    @Override
    public PageResponseDTO<ReplyDTO> pageList(PageRequestDTO pageRequestDTO, Long bno) {
        // 페이징 처리를 들어와서 만드는걸로 하여 pageable을 파라미터에서 제거
        // 하지만 page는 들어오는 것으로 변경

        Pageable pageable =
                pageRequestDTO.getPageable("rno");

        Page<Reply> replyPage =
                replyRepository.findByBoardBno(bno, pageable);

        List<Reply> replyList = replyPage.getContent();

        List<ReplyDTO> replyDTOList =
            replyList.stream()
                .map(reply -> modelMapper.map(reply, ReplyDTO.class)
                    .setBoardDTO(modelMapper.map(reply.getBoard(), BoardDTO.class)))
                        .collect(Collectors.toList());

        PageResponseDTO<ReplyDTO> responseDTO =
                PageResponseDTO.<ReplyDTO>withAll()
                        .dtoList(replyDTOList)
                        .total((int)replyPage.getTotalElements())
                        .pageRequestDTO(pageRequestDTO)
                        .build();

        return responseDTO;
    }

    @Override
    public ReplyDTO update(ReplyDTO replyDTO) {
        // 들어온 replyDTO값을 찍어주고
        log.info("댓글서비스 replyDTO" + replyDTO);

        // 수정할 데이터를 찾는다.
        Reply reply =
            replyRepository.findById(replyDTO.getRno())
                .orElseThrow(EntityNotFoundException::new);

        // 수정할 데이터를 수정한다. // 댓글 내용만 수정할 것이다.
        reply.setReplyText(replyDTO.getReplyText());

        // 없데이트 수행
        // 수정한 뒤에 reply을 replyDTO로 변환해서 반환을 해야지만....
        return modelMapper.map(reply, ReplyDTO.class);
    }

    @Override
    public void remove(Long rno) {
        log.info("들어온 rno값 : " + rno);

//        Reply reply =
//            replyRepository.findById(rno).orElseThrow(EntityNotFoundException::new);
//        replyRepository.delete(reply);
//      위 아래 같은 내용이지만 위에 코드는 예외처리가 가능하다.

        replyRepository.deleteById(rno);

    }

}
