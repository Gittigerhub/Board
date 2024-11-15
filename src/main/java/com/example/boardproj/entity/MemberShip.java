package com.example.boardproj.entity;

import com.example.boardproj.constant.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor @Builder
@Table(name = "user")
public class MemberShip {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;

    @Column(nullable = false)                      // 제약조건
    private String name;        // 이름               not null

    @Column(unique = true, nullable = false)
    private String email;       // 이메일              unique, not null

    @Column(nullable = false)
    private String password;    // 비밀번호            not null

    @Column(nullable = false)
    private String address;     // 주소               not null


    // enum으로 권한
    @Enumerated(EnumType.STRING)
    private Role role;

}
