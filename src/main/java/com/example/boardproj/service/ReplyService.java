package com.example.boardproj.service;

import com.example.boardproj.dto.BoardDTO;
import com.example.boardproj.dto.PageRequestDTO;
import com.example.boardproj.dto.PageResponseDTO;
import com.example.boardproj.dto.ReplyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReplyService {
    // 등록
    public void register(ReplyDTO replyDTO);

    // 읽기
    public ReplyDTO read(Long rno);

    // 목록
    public List<ReplyDTO> list(Long bno);

    // Pageable이 있어야 Page<>타입 사용 가능
    public Page<ReplyDTO> listpage(Long bno, Pageable pageable);

    // 목록 페이징 처리, 일반 컨드롤러 일때


    // 목록 페이징 처리, restController 일때
    public PageResponseDTO<ReplyDTO> pageList(PageRequestDTO pageRequestDTO, Long bno);

    // 수정
    public ReplyDTO update(ReplyDTO replyDTO);

    // 삭제
    public void remove(Long rno);
}
