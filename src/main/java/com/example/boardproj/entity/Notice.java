package com.example.boardproj.entity;

import com.example.boardproj.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nno;       // 글 번호

    @Column(name = "n_title", nullable = false, length = 30)
    private String title;    // 글 제목

    @Column(name = "n_content", nullable = false, length = 255)
    private String content;    // 글 내용

    @Column(name = "n_writer", nullable = false, length = 20)
    private String writer;    // 글 작성자 / 어드민만 권한

}
