package com.jungsu_site.jungsu_tutorial.service;

import com.jungsu_site.jungsu_tutorial.controller.checkID;
import com.jungsu_site.jungsu_tutorial.domain.Member;
import com.jungsu_site.jungsu_tutorial.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    //원래라면 DBMemberRepository로 받아야하는데 지금 interface라서 저렇게 해도 자동으로? DB~로 연결될것이다.
    //저장소 (Repository관련)
    private final MemberRepository memberRepository;
    //세션 (ServerSession관련)
    private final SessionRegistry sessionRegistry;
//    @Autowired
    public MemberService(MemberRepository memberRepository, SessionRegistry sessionRegistry){
        this.memberRepository = memberRepository;
        this.sessionRegistry = sessionRegistry;
    }

    //회원가입 기능 -> 아이디 출력
    public String join(Member member) {
        memberRepository.save(member);
        return member.getId();
    }


    //세션 확인 - 서버용 -> 안됨. 이런건 없나보다.. 다음에 다시 알아보던지 하자. 지쳐
//    public void SessionPrint(Member logined_member, HttpSession session) {
//        session.setAttribute("loginedMember", logined_member.getId());
//        try {
//            Thread.sleep(1000); // 1초 딜레이
//        } catch (InterruptedException e) {
//            System.out.println("뭐 시봉방");
//        }
//        System.out.println(logined_member.getId());
//
//        //넣었던거 바로 빼는건 되긴 함..
////        Object loginedMember2 = session.getAttribute("loginedMember");
////        System.out.println("loginedMember Attribute: " + loginedMember2);
//
//        //유저 세션에 대해 출력
//        List<SessionInformation> sessions = sessionRegistry.getAllSessions(logined_member.getId(), true);
//        System.out.println("세션리스트 출력");
//        System.out.println(sessions);
//        // 세션 정보 출력 또는 다른 작업 수행
//        for (SessionInformation session_info : sessions) {
//            System.out.println("Session ID: " + session_info.getSessionId());
//            // 추가적인 세션 정보 출력 등
//        }
//
//    }



}
