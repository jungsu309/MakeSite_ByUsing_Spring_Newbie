package com.jungsu_site.jungsu_tutorial.repository;

import com.jungsu_site.jungsu_tutorial.domain.ImageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

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

        System.out.println(fileName);
        System.out.println(filePath);
        System.out.println(fileSize);
        System.out.println(userName);

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

        System.out.println(parameters);

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters)); //-- 자동생성되는 키가 잇을때만.
//        jdbcInsert.execute(new MapSqlParameterSource(parameters));


    }

}
