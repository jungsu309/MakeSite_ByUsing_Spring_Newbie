package com.jungsu_site.jungsu_tutorial.service;

import com.jungsu_site.jungsu_tutorial.controller.checkID;
import com.jungsu_site.jungsu_tutorial.domain.Member;
import com.jungsu_site.jungsu_tutorial.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class MemberService {

    //원래라면 DBMemberRepository로 받아야하는데 지금 interface라서 저렇게 해도 자동으로? DB~로 연결될것이다.
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    //회원가입 기능 -> 아이디 출력
    public String join(Member member) {
//        System.out.println(member.getId());
//        System.out.println(member.getPw());
//        System.out.println(member.getNickname());
        //이름 중복 불가능
//        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

//    ID로 중복확인
//    private void validateDuplicateMember(Member member) {
//        memberRepository.findById(member.getId())
//                .ifPresent(m -> {
//                    //중복 아이디인 경우
//                    throw new IllegalStateException("이미 존재하는 회원입니다. 다른 아이디를 이용해주세요.");
//                });
//    }



}
