package com.jungsu_site.jungsu_tutorial.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jungsu_site.jungsu_tutorial.controller.JoinForm;
import com.jungsu_site.jungsu_tutorial.controller.MemberController;
import com.jungsu_site.jungsu_tutorial.domain.ImageEntity;
import com.jungsu_site.jungsu_tutorial.domain.Member;
import com.jungsu_site.jungsu_tutorial.repository.DBBackGroundRepository;
import com.jungsu_site.jungsu_tutorial.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BgImgTest {

    @Autowired
    private MockMvc mockMvc;

    SaveImageService saveImageService;

    @Mock
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberController memberController;

    @Autowired
    private DBBackGroundRepository dbBackGroundRepository;

    @Test
    @Transactional
    void join_login_bgImg() throws Exception {
        // 1. 회원가입 (repository로 바로 넣음)
        Member member = new Member();
        member.setId("testUser");
        member.setPw("testPassword");
        member.setNickname("testNickname");
        String saveId = memberService.join(member);


        // 사용자로부터 입력받음. -> 2. 로그인
        // Create a sample JoinForm
        JoinForm joinForm = new JoinForm();
        joinForm.setId("testUser");
        joinForm.setPw("testPassword");


        // Perform the MockMVC request
        // login과정 -> db에서 같은 id를 가진 데이터를 찾아냄.
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(joinForm)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"message\":\"안녕하세요 testUser님.\\n환영합니다!\"}"));


        //3. img입력
        // 파일정보
        MockMultipartFile file = new MockMultipartFile(
                "image", "test_image.jpg", "image/jpeg", "file_content".getBytes());
        //파일 정보를 토대로 저장했다고 가정 -> DB에 저장
        saveImageService.saveImage(file, "testUser");

        ImageEntity imageEntity = new ImageEntity();

        imageEntity.setFileName("test_image.jpg");
        imageEntity.setFilePath("test/path");
        imageEntity.setFileSize(1024);
        imageEntity.setUserName("testUser");
        LocalDateTime currentTime = LocalDateTime.now();
        imageEntity.setCurrentTime(currentTime);

        saveImageService.findImagesById("testUser");


    }
}
