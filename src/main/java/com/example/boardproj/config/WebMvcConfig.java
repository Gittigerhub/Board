package com.example.boardproj.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 서버 자체에서 보안으로 서버안 이미지 경로에 접근을 못하게함으로 해당경로를 다른 임의의 경로로 변경
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    // lombok꺼 말고 org꺼로
    @Value("${uploadPath}") // application.properties에 등록한 내용들이 들어갈 수 있음
    String uploadPath;

    // 재정의
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/images/**")
                .addResourceLocations(uploadPath);


    }
}
