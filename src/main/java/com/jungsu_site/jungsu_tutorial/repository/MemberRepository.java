package com.jungsu_site.jungsu_tutorial.repository;

import com.jungsu_site.jungsu_tutorial.domain.Member;

import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(String id);

    Member login(String id, String pw);

    //Member logout(Member member);
//    Member nickname_modify(Member member);
}
