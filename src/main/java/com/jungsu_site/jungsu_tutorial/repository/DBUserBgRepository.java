package com.jungsu_site.jungsu_tutorial.repository;

import com.jungsu_site.jungsu_tutorial.domain.ImageEntity;
import com.jungsu_site.jungsu_tutorial.domain.UserNowBgEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DBUserBgRepository {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public DBUserBgRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // DB 행 추가 (생성)
    public void saveToDB(UserNowBgEntity userNowBgEntity) {

        String id = userNowBgEntity.getId();
        String filePath = userNowBgEntity.getFilePath();


        System.out.println(id);
        System.out.println(filePath);


        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        //자동으로 만들어지는 키없으면 이렇게 세팅
        jdbcInsert.withTableName("User_now_Bg");
        //ID에 member로부터 id를 받아와서 쌍으로 parameters에 넣어둔다.
        //id외에도 다른 데이터들도 동일하게 parameters에 넣어주자.
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);
        parameters.put("file_path", filePath);

        System.out.println(parameters);

        // 데이터베이스에 저장
         jdbcInsert.execute(new MapSqlParameterSource(parameters));

    }

    // DB row 값 수정 (1:1로 수정함.)
    public void modifyDB(UserNowBgEntity userNowBgEntity){
        String sql = "UPDATE User_now_Bg SET file_path = ? WHERE id = ?";
        jdbcTemplate.update(sql, userNowBgEntity.getFilePath(), userNowBgEntity.getId());

    }
    public Optional<UserNowBgEntity> findById(String userId){
        List<UserNowBgEntity> result = jdbcTemplate.query("select * from User_now_Bg where id =?",
                imageRowMapper(), userId);
        return result.stream().findAny(); // -> 여러개중에 하나 할때만.

//        return jdbcTemplate.query("select * from User_now_Bg where ID =?",
//                imageRowMapper(), userId); ->여러개 출력할 때.
    }

    private RowMapper<UserNowBgEntity> imageRowMapper() {
        return (rs, rowNum) -> {

            UserNowBgEntity userNowBgEntity = new UserNowBgEntity();
            userNowBgEntity.setId(rs.getString("id"));
            userNowBgEntity.setFilePath(rs.getString("file_path"));

            return userNowBgEntity;
        };
    }
}
