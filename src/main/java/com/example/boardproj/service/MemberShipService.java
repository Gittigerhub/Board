package com.example.boardproj.service;

import com.example.boardproj.constant.Role;
import com.example.boardproj.dto.MemberShipDTO;
import com.example.boardproj.entity.MemberShip;
import com.example.boardproj.repository.MemberShipRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor @Log4j2 @Transactional
public class MemberShipService implements UserDetailsService {

    // 의존성 주입
    private final MemberShipRepository memberShipRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    // 회원가입
    public MemberShipDTO saveMember(MemberShipDTO memberShipDTO) {

        // 중복계정인지 확인
        MemberShip memberShip =
                memberShipRepository.findByEmail(memberShipDTO.getEmail());
        if (memberShip != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }

        // 중복계정이 아닐경우
        memberShip =
                modelMapper.map(memberShipDTO, MemberShip.class);

        // 일반유저로만 지정
        memberShip.setRole(Role.USER);

        // 비밀번호 암호화
        memberShip.setPassword(passwordEncoder.encode(memberShipDTO.getPassword()));

        //저장
        memberShip =
                memberShipRepository.save(memberShip);

        // DTO로 변환해서 반환
        return modelMapper.map(memberShip, MemberShipDTO.class);

    }

    // 로그인
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // 사용자가 입력한 email이 들어왔는지 확인
        log.info("유저디테일 서비스로 들어온 이메일 : " + email);

        // 사용자가 입력한 email로 DB에서 email과 password가 포함된
        // entitiy 정보를 가져오기

        // entity에서 들어온 email값과 같은 데이터 찾기
        MemberShip memberShip =
                memberShipRepository.findByEmail(email);

        // 가입한 계정이 아닐 경우 예외처리
        if (memberShip == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        // 회원이 찾은 정보가 뭔지 확인
        log.info("현재 찾은 회원정보 : " + memberShip);

        //권한 처리
        String role = "";
        if ("ADMIN".equals(memberShip.getRole().name())) {
            log.info("관리자");
            role = Role.ADMIN.name();
        } else {
            log.info("일반유저");
            role = Role.USER.name();
        }

        // 로그인이 정상적이라면 로그인 처리될 수있도록 값 반환
        return User.builder()
                .username(memberShip.getEmail())
                .password(memberShip.getPassword())
                .roles(role)
                .build();
    }


    // 로그아웃





}
