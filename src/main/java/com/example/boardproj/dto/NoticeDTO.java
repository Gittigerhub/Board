package com.example.boardproj.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeDTO {

    private Long nno;               // 글 번호

    @NotBlank(message = "제목은 공백일수 없습니다.")
    @Size(message = "제목의 길이는 2~30까지입니다.", min = 2, max = 30)
    private String title;           // 글 제목

    @NotBlank(message = "내용은 공백일수 없습니다.")
    @Size(message = "내용의 길이는 2~255까지입니다.", min = 2, max = 255)
    private String content;         // 글 내용

    @NotBlank(message = "작성자는 공백일수 없습니다.")
    @Size(message = "작성자의 길이는 2~20까지입니다.", min = 2, max = 20)
    private String writer;          // 글 작성자 / 어드민만 권한

    private LocalDate regdate;      // 업로드 날짜

    private LocalDate updatedate;   // 수정 날짜

}
