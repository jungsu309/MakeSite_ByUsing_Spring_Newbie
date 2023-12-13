package com.jungsu_site.jungsu_tutorial.controller;

import com.jungsu_site.jungsu_tutorial.domain.ImageEntity;
import com.jungsu_site.jungsu_tutorial.service.MemberService;
import com.jungsu_site.jungsu_tutorial.service.SaveImageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @ResponseBody
    public List<String> handleFileUpload(@RequestParam("Fileinput") MultipartFile file, HttpServletRequest request, Model model) {
        // 이미지를 저장하고 DB에 저장하는 로직을 추가하세요.
        // 예를 들면, 서비스 클래스를 통해 이미지를 저장하고 DB에 저장하는 기능을 호출합니다.
        String sessionAttribute = memberService.getSessionAttribute(request, "userID");

        try {
            saveimageService.saveImage(file, sessionAttribute);
        } catch (IOException e) {
            e.printStackTrace(); // 실제 상황에 따라 적절한 예외 처리 로직을 추가해야 합니다.
            // 예외 처리를 위해 사용자에게 알림을 보낼 수도 있습니다.
//            return "redirect:/BgImgMenu/error?message=Image+upload+failed"; //chatgpt가 알려준 오류알림.
            //일단 비워두고싶음
        }
//        return "redirect:/BgImgMenu";

        // 이미지 여러개
        List<ImageEntity> ImageList = saveimageService.findImagesById(sessionAttribute);

        //ajax를 이용해서 비동기적으로 전송하기 때문에 model로 보낼 필요가 없다.
//        model.addAttribute("images", ImageList);
        List<String> imagePathsOrUrls = ImageList.stream()
                .map(ImageEntity::getFilePath) // 또는 .getImageUrl()# + "\\" + imageEntity.getFileName()
                .collect(Collectors.toList());

        System.out.println("image-list!!!!");
        System.out.println(imagePathsOrUrls);

        return imagePathsOrUrls;

//        return "SettingBackgroundImgMenu";
//        return "Json view";
//        return ImageList;
    }



//    @GetMapping("/BgImgMenu")
//    public String handleFileUpload_page(){
//        return "redirect:/";
//
//    }
}
