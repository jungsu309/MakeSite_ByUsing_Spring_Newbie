package com.jungsu_site.jungsu_tutorial.service;

import com.jungsu_site.jungsu_tutorial.controller.checkID;
import com.jungsu_site.jungsu_tutorial.domain.Member;
import com.jungsu_site.jungsu_tutorial.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    //현재 세션을 이용하여 userid를 가져오는 방법. 현재 세션을 확인할 수 있다.
    public String getSessionAttribute(HttpServletRequest request, String attributeName) {
        HttpSession session = request.getSession();
        System.out.println((String) session.getAttribute(attributeName));
        return (String) session.getAttribute(attributeName);
    }



}
