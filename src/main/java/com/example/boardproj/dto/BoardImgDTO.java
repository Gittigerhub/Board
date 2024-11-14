package com.example.boardproj.dto;

import com.example.boardproj.entity.Board;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardImgDTO {


    private Long ino;


    private String imgPath;


    private String imgName;


    private String newImgName;


    private BoardDTO boardDTO;


}
