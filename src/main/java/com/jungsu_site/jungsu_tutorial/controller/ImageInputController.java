package com.jungsu_site.jungsu_tutorial.controller;

import com.jungsu_site.jungsu_tutorial.domain.ImageEntity;
import com.jungsu_site.jungsu_tutorial.service.MemberService;
import com.jungsu_site.jungsu_tutorial.service.SaveImageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ImageInputController {


    private final SaveImageService saveimageService;
    private final MemberService memberService;

    public ImageInputController(SaveImageService saveimageService, MemberService memberService) {
        this.saveimageService = saveimageService;
        this.memberService = memberService;
    }


    @PostMapping("/BgImgMenu")
    public String BGMenu(){
        return "ImageInput";
    }

    @PostMapping("/BgImgMenu/inputImg")
    @ResponseBody
    public String FileUploadToServer(@RequestParam("Fileinput") MultipartFile file, HttpServletRequest request) throws IOException {
        // 이미지를 저장하고 DB에 저장하는 로직을 추가하세요.
        // 예를 들면, 서비스 클래스를 통해 이미지를 저장하고 DB에 저장하는 기능을 호출합니다.
        String sessionAttribute = memberService.getSessionAttribute(request, "userID");
        saveimageService.saveImage(file, sessionAttribute);
        return "File uploaded successfully.";

    }

    @GetMapping("/BgImgMenu/getImg")
    public ResponseEntity<List<String>> sendFilePath(HttpServletRequest request){
        String sessionAttribute = memberService.getSessionAttribute(request, "userID");
        // 이미지 여러개
        List<ImageEntity> ImageList = saveimageService.findImagesById(sessionAttribute);
        System.out.println("id로 ImageEnity 정보 가져오기");
        System.out.println(ImageList);

        //ajax를 이용해서 비동기적으로 전송하기 때문에 model로 보낼 필요가 없다.
//        model.addAttribute("images", ImageList);
        List<String> imagePathsOrUrls = ImageList.stream()
                .map(ImageEntity::getFilePath) // 또는 .getImageUrl()# + "\\" + imageEntity.getFileName()
                .collect(Collectors.toList());


        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl("no-cache");

        System.out.println("ImageEnity정보중에서 Path 정보만 가져오기");
        System.out.println(imagePathsOrUrls);

        return new ResponseEntity<>(imagePathsOrUrls, headers, HttpStatus.OK);

    }

}
