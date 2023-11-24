package com.jungsu_site.jungsu_tutorial.service;

import com.jungsu_site.jungsu_tutorial.domain.Member;
import com.jungsu_site.jungsu_tutorial.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
//@Transactional : 테스트할때마다 누적안되게 지워주는 역할
//@Transactional
class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;


    @Test
    void 회원가입(){
        //given : 멤버 정보
        Member member = new Member();
        member.setId("jungsu");
        member.setPw("asdf");
        member.setNickname("jszzang");

        //when : 회원가입 함수 -> Id리턴
        String saveId = memberService.join(member);

        //then : 저장했던 findMember의 saveId가 현재 Id와 같은지??확인?? 왜확인함?..
        //아직 고려하지 않을것이다. 나중에 테스트.

    }

}
