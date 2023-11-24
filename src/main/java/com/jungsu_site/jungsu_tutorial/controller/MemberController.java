package com.jungsu_site.jungsu_tutorial.controller;

import com.jungsu_site.jungsu_tutorial.domain.Member;
import com.jungsu_site.jungsu_tutorial.repository.MemberRepository;
import com.jungsu_site.jungsu_tutorial.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.Optional;


//비밀번호 해싱

@Controller
public class MemberController {

    private MemberService memberService;
    private MemberRepository memberRepository;

    //이거 안하니까 저장이 안됨.
    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    //원래 여기서는 memberservice위주로 했는데.. 내가 여기다 중복확인까지 넣어버려서 repository넣어줘야하나봄.
    @Autowired
    public MemberController(MemberService memberService, MemberRepository memberRepository) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
    }

    //url
    @GetMapping("/join")
    public String craeteJoinForm(){
        //html이름
        return "join";
    }


    @GetMapping("/join/check_id")
    public String createCheckId(){
        //html이름
        return "check_id";
    }


    //db에서 꺼내와서 비교하고 상황에 따른 메세지 출력함.
    //이거 딱히 이동할 페이지 없어서 void로 하고  return없이 두니까 계속 안됐었음..;;;.. 그냥 지정해줌 그래서 ㅅㅂ
    //이동이 필요없는것 -> 비동기 인듯. 아무리해도 잘 안돼서;; ajax사용하려고함.
    @PostMapping("/join/check_id")
    public ResponseEntity<String> CheckIdDuplicated(@RequestBody checkID checkid) {
        System.out.println(checkid);
        System.out.println(checkid.getId());
        Optional<Member> IsDuplicated = memberRepository.findById(checkid.getId());
        String message;
        if (IsDuplicated.isPresent()) {
            message = "이미 존재하는 회원입니다.";
        } else {
            message = "중복확인이 완료되었습니다. 회원가입을 계속 진행해 주세요.";
        }
        //메세지전송
        return ResponseEntity.ok("{\"message\":\"" + message + "\"}");
    }


    //입력받은 값 처리
    @PostMapping("/join")
    public String JoinMember(JoinForm form){
        Member member = new Member();
        //id저장.
        member.setId(form.getId());
        //비밀번호 해싱해서 저장.
        String hashedPassword = new BCryptPasswordEncoder().encode(form.getPw());
        member.setPw(hashedPassword);
        //닉네임(별명)저장.
        member.setNickname(form.getNickname());
        //저장
        memberService.join(member);
        //가입완
        return "redirect:/";
    }

    @GetMapping("/login")
    public String Login(){
        //html이름
        return "login";
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody JoinForm form) {
        System.out.println(form);
        System.out.println(form.getId());
        System.out.println(form.getPw());
        Member logined_member = memberRepository.login(form.getId(), form.getPw());
        String message;
        //로그인 성공
        if (logined_member != null) {
            //아이디 발견
            message = "안녕하세요 " + logined_member.getId() + "님.\\n환영합니다!";

        } else {
            message = "올바르지 않은 ID 혹은 PW입니다. 다시 시도해주세요.";
        }
        //메세지전송
        return ResponseEntity.ok("{\"message\":\"" + message + "\"}");
    }


    @GetMapping("/main_page")
    public String LoginSuccess(){
        //html이름
        return "mainPage";
    }

}
