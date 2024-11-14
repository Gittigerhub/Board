package com.example.boardproj.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ino;


    private String imgPath;


    private String imgName;

    // uuid 사용시
    private String newImgName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bno")
    private Board board;

}
