package com.example.boardproj.service;

import com.example.boardproj.dto.BoardDTO;
import com.example.boardproj.dto.BoardImgDTO;
import com.example.boardproj.entity.Board;
import com.example.boardproj.entity.BoardImg;
import com.example.boardproj.repository.BoardImgRepository;
import com.example.boardproj.repository.BoardRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardImgService {

    private final BoardImgRepository boardImgRepository;
    private final BoardRepository boardRepository;

    private final FileService fileService;
    private final ModelMapper modelMapper;

    @Value("${imgLocation}")
    private String imgLocation;

    public void boardImgregister(Long bno, MultipartFile multipartFile) {
        // 원래 이름 가져오기   //
        String originalFilename =
            multipartFile.getOriginalFilename();
        // 파일명에 담긴 경로 삭제
        // ex) "file:///C:/upload/a.png"    // indexOf 앞에서 부터 , lastindexOf 뒤에서 부터
        String imgName =
            originalFilename.substring(originalFilename.lastIndexOf("\\")+1);
                                                    // 뒤에서 처음 나오는 / 부분은 -6 그래서 a.png만 자르기 위해서 a위치로 +1 해줘야 함


        // 특별한 난수
//        UUID uuid = UUID.randomUUID();
        String uuid = UUID.randomUUID().toString();
        log.info("생성된 uuid" + uuid);
        // 중복되지 않도록 파일이름을 변경해서 저장DB, 물리적 파일 둘다
        String newImgName =
//                uuid.toString() + "";     위에서 다르게 선언하여 toSrting붙일 필요가 없어짐
                uuid + "_" + imgName;

        Board board =
            boardRepository.findById(bno).orElseThrow(EntityNotFoundException::new);


        // 참조대상인 board를 가지고 boardImg를 만들어 저장한다.
        BoardImg boardImg = new BoardImg();
        boardImg.setBoard(board);   // 참조하는 글번호

                                            // c:/upload + "// + "board"
        boardImg.setImgPath(imgLocation); // 저장 경로    // 각 경로board notice member등은
                                        // fileservice를 만들때무터 파라미터로 받도록 한다.

        boardImg.setNewImgName(newImgName);   // 새로운 이름
        boardImg.setImgName(imgName);   // 사진 이름

        // DB저장
        boardImgRepository.save(boardImg);

        // 물리적인 파일저장    /   서버의 저장소에 저장 ex)/upload/폴더
        fileService.register(multipartFile, newImgName);
    }

    public List<BoardImgDTO> boardImgread(Long bno) {

        // select * from img where img.board.bno = :bno

        List<BoardImg> boardImgList =   // bno에 있는 이미지 전부를 가져옴
            boardImgRepository.findByBoardBno(bno);

        List<BoardImgDTO> boardImgDTOList =     // DTO로 반환
            boardImgList.stream()
                .map(boardImg -> modelMapper.map(boardImg, BoardImgDTO.class))
                    .collect(Collectors.toList());

        return boardImgDTOList;

    }

    public void del(Long ino) {

        // DB 정보 찾아오기   // 사진 정보 찾아오기
        BoardImg boardImg =
            boardImgRepository.findById(ino).get(); // 예외처리 필요  / 지금은 get, 진행 안하였음

        // 경로 가져오기
        String path =
            imgLocation + "\\" + boardImg.getNewImgName();  // 경로이기 때문에 "\\" 필요
        fileService.delFile(path);  // 물리적 데이터 삭제   /   서버 저장소에서 물리적으로 저장된 파일 삭제

        boardImgRepository.deleteById(ino); // DB 삭제

    }


}
