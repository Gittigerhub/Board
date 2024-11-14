package com.example.boardproj.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

// 빈등록을 못받아오는거 같아서 Component 적용
@Component
public class FileService {

    @Value("${imgLocation}")
    private String imgLocation;

    public void register(MultipartFile multipartFile, String newImgName) {
        // 들어온 사진이름
//        String imgname =
//            multipartFile.getOriginalFilename();
//        System.out.println(imgname);
//        System.out.println(imgname);
//        System.out.println(imgname);
//        System.out.println(imgname);
        // 오리지널 네임 주석 / 입력받은 네임으로 설정

        System.out.println(imgLocation + "\\" + newImgName);

        // 사진 경로 및 이름
        File file = new File(imgLocation + "\\" + newImgName);
        // 사진저장

        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {

        }

    }

    // 물리 파일삭제
    public void delFile(String filepath) {

        File deleteFilekkk = new File("파일경로");

        // if( 파일객체변수명 deleteFilekkk가 존재한다면~~)
        if (deleteFilekkk.exists()) {
            deleteFilekkk.delete(); // 물리 파일삭제
            System.out.println("파일 삭제 함");
            System.out.println("파일 삭제 함");
            System.out.println("파일 삭제 함");
        }else {
            System.out.println("파일 못 삭제 함");
            System.out.println("파일 못 삭제 함");
            System.out.println("파일 못 삭제 함");
        }

    }



}
