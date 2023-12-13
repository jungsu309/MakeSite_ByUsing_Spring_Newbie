package com.jungsu_site.jungsu_tutorial.repository;

import com.jungsu_site.jungsu_tutorial.domain.ImageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DBBackGroundRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DBBackGroundRepository (DataSource dataSource){

        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void saveToDB(ImageEntity imageEntity) {

        String fileName = imageEntity.getFileName();
        String filePath = imageEntity.getFilePath();
        long fileSize = imageEntity.getFileSize();
        String userName = imageEntity.getUserName();
        LocalDateTime currentTime = imageEntity.getCurrentTime();

        System.out.println(fileName);
        System.out.println(filePath);
        System.out.println(fileSize);
        System.out.println(userName);
        System.out.println(currentTime);


        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        //member 테이블에 기본키?(자동으로 만들어지는 키) Auto_serial_number을 넣어준다.
        jdbcInsert.withTableName("Background_img").usingGeneratedKeyColumns("Auto_serial_number");
        //ID에 member로부터 id를 받아와서 쌍으로 parameters에 넣어둔다.
        //id외에도 다른 데이터들도 동일하게 parameters에 넣어주자.
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("file_name", fileName);
        parameters.put("file_path", filePath);
        parameters.put("file_size", fileSize);
        parameters.put("ID", userName);
        parameters.put("creation_time", currentTime); // 현재시간 추가

        System.out.println(parameters);

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters)); //-- 자동생성되는 키가 잇을때만.
//        jdbcInsert.execute(new MapSqlParameterSource(parameters));


    }
    public List<ImageEntity> findById(String userId){
//        List<ImageEntity> result = jdbcTemplate.query("select * from Background_Img where user_name =?",
//                imageRowMapper(), userId);
//        return result.stream().findAny(); -> 여러개중에 하나 할때만.

        return jdbcTemplate.query("select * from Background_Img where ID =?",
                imageRowMapper(), userId);
    }

    private RowMapper<ImageEntity> imageRowMapper() {
        return (rs, rowNum) -> {

            ImageEntity imageEntity = new ImageEntity();
            imageEntity.setFileName(rs.getString("file_name"));
            imageEntity.setFilePath(rs.getString("file_path"));
            imageEntity.setUserName(rs.getString("id"));
            imageEntity.setFileSize(rs.getLong("file_size"));
            imageEntity.setCurrentTime(rs.getTimestamp("creation_time").toLocalDateTime());

            return imageEntity;
        };
    }
}
