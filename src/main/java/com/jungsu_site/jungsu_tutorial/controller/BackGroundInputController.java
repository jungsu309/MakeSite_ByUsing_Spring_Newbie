package com.jungsu_site.jungsu_tutorial.controller;

import com.jungsu_site.jungsu_tutorial.service.MemberService;
import com.jungsu_site.jungsu_tutorial.service.SaveImageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class BackGroundInputController {


    private final SaveImageService saveimageService;
    private final MemberService memberService;

    public BackGroundInputController(SaveImageService saveimageService, MemberService memberService) {
        this.saveimageService = saveimageService;
        this.memberService = memberService;
    }


    @PostMapping("/BgImgMenu")
    public String BGMenu(){
        return "SettingBackgroundImgMenu";
    }

    @PostMapping("/BgImgMenu/inputBgImg")
    public String handleFileUpload(@RequestParam("Fileinput") MultipartFile file, HttpServletRequest request) {
        // 이미지를 저장하고 DB에 저장하는 로직을 추가하세요.
        // 예를 들면, 서비스 클래스를 통해 이미지를 저장하고 DB에 저장하는 기능을 호출합니다.
        String sessionAttribute = memberService.getSessionAttribute(request, "userID");

        try {
            saveimageService.saveImage(file, sessionAttribute);
        } catch (IOException e) {
//            e.printStackTrace(); // 실제 상황에 따라 적절한 예외 처리 로직을 추가해야 합니다.
            // 예외 처리를 위해 사용자에게 알림을 보낼 수도 있습니다.
            return "redirect:/BgImgMenu/error?message=Image+upload+failed"; //chatgpt가 알려준 오류알림.
        }
        return "redirect:/BgImgMenu";
    }

    @GetMapping("/BgImgMenu")
    public String handleFileUpload_page(){
        return "mainPage";
    }
}
