package com.example.boardproj.dto;

import com.example.boardproj.constant.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString @Builder
public class MemberShipDTO {

    private Long num;

    @NotBlank(message = "공백일 수 없습니다. 이름을 작성해주세요")
    @Size(min = 2, max = 10)
    private String name;        // 이름

    @NotBlank(message = "공백일 수 없습니다. 이메일을 작성해주세요")
    @Email(message = "이메일 형식에 맞춰서 작성하시오")
    @Size(max = 50, message = "최대 50글자 이내로 작성하셔야 합니다.")
    private String email;       // 이메일

    @NotBlank(message = "공백일 수 없습니다. 비밀번호를 작성해주세요")
    @Size(min =10, max = 16, message = "최소 10~ 최대 16 이내로 비밀번호를 작성하셔야 합니다.")
    private String password;    // 비밀번호

    @NotBlank(message = "공백일 수 없습니다. 주소를 작성해주세요")
    private String address;     // 주소

    private Role role;

}
