package com.jungsu_site.jungsu_tutorial.repository;

import com.jungsu_site.jungsu_tutorial.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DBMemberRepository implements MemberRepository{

    //회원정보 저장하기 위해 jdbc사용.
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DBMemberRepository (DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //회원정보 저장 : save ->  테이블이름 : js_site_member
    @Override
    public Member save(Member member) {
        // 데이터를 저장하기 위한 구현체
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        //member 테이블에 기본키?(자동으로 만들어지는 키) Auto_serial_number을 넣어준다.
        jdbcInsert.withTableName("js_site_member").usingGeneratedKeyColumns("Auto_serial_number");

        //ID에 member로부터 id를 받아와서 쌍으로 parameters에 넣어둔다.
        //id외에도 다른 데이터들도 동일하게 parameters에 넣어주자.
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ID", member.getId());
        parameters.put("PW", member.getPw());
        parameters.put("NickName", member.getNickname());


        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        member.setAuto_serial_number(key.longValue());
        // 위 코드 대신에 아래처럼 작성하기도 한다고 한다.
//        SqlParameterSource parameters = new MapSqlParameterSource()
//                .addValue("ID", member.getId())
//                .addValue("PW", member.getPw())
//                .addValue("NickName", member.getNickname());

        return member;
    }

    @Override
    public Optional<Member> findById(String id) {
        List<Member> result = jdbcTemplate.query("select * from JS_SITE_MEMBER where ID =?", memberRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Member login(String id, String pw) {
        List<Member> result = jdbcTemplate.query("select * from JS_SITE_MEMBER where ID =?", memberRowMapper(), id);

        if (!result.isEmpty()) {
            Member member = result.get(0);
            String storedPw = member.getPw();

            //비번 비교
            boolean isPasswordMatch = new BCryptPasswordEncoder().matches(pw, storedPw);

            //로그인 성공
            if (isPasswordMatch) {
                return member;
            }
        }

        //로그인 실패 -> id가 없거나 pw가 잘못된 경우에 해당.
        return null;
    }

//    @Override
//    public Optional<Member> login(String id, String pw) {
//        List<Member> result = jdbcTemplate.query("select * from JS_SITE_MEMBER where ID =?", memberRowMapper(), id);
//        Optional<Member> find_id = result.stream().findAny();
//
//        Optional<Member> pw = jdbcTemplate.query("select  from JS_SITE_MEMBER where ID =?", memberRowMapper(), id);
//        return result.stream().findAny();
//    }


//    @Override
//    public Member nickname_modify(Member member) {
//        return null;
//    }


    // 이건 왜 하는건지? 모르겠음...
    private RowMapper<Member> memberRowMapper(){
        return (rs, rowNum) -> {

            Member member = new Member();
            member.setAuto_serial_number(rs.getLong("Auto_serial_number"));
            member.setId(rs.getString("ID"));
            member.setPw(rs.getString("PW"));
            member.setNickname(rs.getString("NickName"));

            return member;
        };

    }
}
