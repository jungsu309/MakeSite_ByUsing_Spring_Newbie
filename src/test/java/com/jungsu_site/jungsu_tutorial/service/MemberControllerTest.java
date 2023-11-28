package com.jungsu_site.jungsu_tutorial.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jungsu_site.jungsu_tutorial.controller.JoinForm;
import com.jungsu_site.jungsu_tutorial.controller.MemberController;
import com.jungsu_site.jungsu_tutorial.domain.Member;
import com.jungsu_site.jungsu_tutorial.repository.MemberRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@Rollback // Add this annotation to rollback after all tests
public class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberController memberController;

    // You can add more test cases for different scenarios (e.g., login failure)

    @Test
//    @Transactional
    void testJoin()throws Exception {
        JoinForm joinForm = new JoinForm();
        joinForm.setId("testUser");
        joinForm.setPw("testPassword");
        joinForm.setNickname("testNickname");

        // Perform the MockMVC request
        mockMvc.perform(MockMvcRequestBuilders.post("/join")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", joinForm.getId())
                        .param("pw", joinForm.getPw())
                        .param("nickname", joinForm.getNickname()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection()) // Redirect status code
                .andExpect(MockMvcResultMatchers.redirectedUrl("/")); // Redirected URL

        // Optionally, you can add further assertions based on your requirements
        // For example, check if the user is actually added to the database
        Optional<Member> find_member = memberRepository.findById("testUser");
        find_member.ifPresent(member -> {
            System.out.println("DB에서 찾음");
            String hashedPassword = new BCryptPasswordEncoder().encode("testPassword");
            Assertions.assertNotNull(member);
            Assertions.assertEquals("testUser", member.getId());
            Assertions.assertEquals(hashedPassword, member.getPw());
            Assertions.assertEquals("testNickname", member.getNickname());
        });

        if (find_member.isEmpty()) {
            System.out.println("DB에서 못찾음. 회원가입 실패");
        }
    }

    @Test
    @Transactional
    void testLoginSuccess() throws Exception {
        // 회원가입 (repository로 바로 넣음)
        Member member = new Member();
        member.setId("testUser");
        member.setPw("testPassword");
        member.setNickname("testNickname");
        String saveId = memberService.join(member);


        // 사용자로부터 입력받음.
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
    }

}
