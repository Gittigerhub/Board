package com.example.boardproj.entity;

import com.example.boardproj.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Reply extends BaseEntity {

    @Id
//  @Column(name = "reply_num")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String replyText;

    private String writer;  // 이것도 나중에, 게시판도 나중에 회원을 참조하자

    // fetch : 실행   / LAZY : 늦는 타입
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bno")
    private Board board;    // pk값으로 객체를 가져와서 담을 객체타입으로 작성하여 참조한다.


    // entity와 vo는 같지만 vo는 테이블 생성안해주고 entity는 테이블을 생성해준다.
    // entity는 db와 연결되는 클래스일 뿐이다.


}
