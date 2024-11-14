package com.example.boardproj.repository.search;

import com.example.boardproj.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface BoardSearch {

    Page<Board> searchAll(String[] types, String keyword, Pageable pageable);
}
