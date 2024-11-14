package com.example.boardproj.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GitTest2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gno2;

    @Column(length = 20, nullable = false)
    private String name2;

    @Column(length = 50, nullable = false)
    private String email2;

    @Column(length = 16, nullable = false)
    private String password2;

    private LocalDate updatedate2;

}
