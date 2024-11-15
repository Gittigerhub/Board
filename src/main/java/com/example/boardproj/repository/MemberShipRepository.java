package com.example.boardproj.repository;

import com.example.boardproj.entity.MemberShip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberShipRepository extends JpaRepository<MemberShip, Long> {

    public MemberShip findByEmail(String email);
    // select * from user where email = :email
}
