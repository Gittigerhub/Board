package com.example.boardproj.dto;

import com.example.boardproj.entity.Board;
import com.example.boardproj.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO extends BaseEntity {


    private Long rno;

    private Long bno;   // 가져오고 싶은값 박히나 꺼내봄

    @NotNull
    @NotEmpty
    @NotBlank(message = "댓글은 공백일 수 없습니다.")
    @Size(min = 2, max = 255, message = "댓글은 2~255 글자 사이여야합니다.")
    private String replyText;

    @NotBlank(message = "작성자는 공백일 수 없습니다.")
    @Size(min = 2, max = 255, message = "댓글은 2~255 글자 사이여야합니다.")
    private String writer;      // 이것도 나중에 게시판도 나중에 회원을 참조하자

    private BoardDTO boardDTO;  // dto는 dto로 받는게 오류 예방에 좋음

    // 날짜

    private LocalDate regdate;

    private LocalDate updatedate;

    // 엔티티와 dto에 선언된것이 달라 지속적으로 엔티티로 반환이 안되어 setter 생성
    public ReplyDTO setBoardDTO(BoardDTO boardDTO) {
        this.boardDTO = boardDTO;
        return this;
    }
}
