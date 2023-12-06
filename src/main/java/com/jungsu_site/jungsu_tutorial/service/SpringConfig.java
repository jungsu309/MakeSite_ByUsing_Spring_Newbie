package com.jungsu_site.jungsu_tutorial.service;

import com.jungsu_site.jungsu_tutorial.repository.DBBackGroundRepository;
import com.jungsu_site.jungsu_tutorial.repository.DBMemberRepository;
import com.jungsu_site.jungsu_tutorial.repository.MemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {


//    db를 사용할 수 있도록 생성자 DI 해줌
    private final DataSource dataSource;
    public SpringConfig(DataSource dataSource){
        this.dataSource = dataSource;
    }


    //MemberService는 @Service로 어노테이션걸어놨음.

    //저장소를 연결해주자.
    @Bean
    public MemberRepository memberRepository(){
        return new DBMemberRepository(dataSource);
    }

    //세션 서버용 확인하기 위해 필요
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public DBBackGroundRepository dbBackGroundRepository(){
        return new DBBackGroundRepository(dataSource);
    }

}
