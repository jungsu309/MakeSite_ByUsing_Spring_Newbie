package com.jungsu_site.jungsu_tutorial.service;

import com.jungsu_site.jungsu_tutorial.domain.ImageEntity;
import com.jungsu_site.jungsu_tutorial.repository.DBBackGroundRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class SaveImageService {

    private final DBBackGroundRepository bgRepository;

    //세션 가져오기 위해서 현재 로그인된 사람 id 필요
    private MemberService memberService;

    // 생성자
    public SaveImageService(DBBackGroundRepository bgRepository, MemberService memberService){
        this.bgRepository = bgRepository;
        this.memberService = memberService;
    }

    //회원가입 기능 -> 아이디 출력
    public void saveImage(MultipartFile file, String sessionAttribute) throws IOException {
        String fileName = file.getOriginalFilename();

        if (fileName == null || fileName.isEmpty()) {
            // 에러 처리 또는 기본값 설정
            throw new IllegalArgumentException("파일 이름이 유효하지 않습니다.");
            // 또는 fileName = "defaultFileName.jpg";
        }

        long fileSize = file.getSize();
        //내가 지정해주는거다.
        //static까지가 기본 경로이기 때문에? 그 이후부터..해주자..ㅠㅠㅠ
//        "src", "main", "resources", "static"
//        String uploadPath = "src/main/resources/static/Uploads/BackGroundImage";
        //아래거는 절대경로;;
        Path uploadPath = Paths.get("src", "main", "resources", "static", "Uploads", "BackGroundImage").toAbsolutePath();
        //이게 상대경로
//        Path uploadPath = Paths.get("Uploads", "BackGroundImage");
        String str_path = "/Uploads/BackGroundImage";//localhost:8080
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setFileName(fileName);
        imageEntity.setFilePath(str_path + "/" + fileName);//+"/"+fileName
        imageEntity.setFileSize(file.getSize());
        imageEntity.setUserName(sessionAttribute);


        // 파일 복사 -> path에 저장
        Path destinationPath = uploadPath.resolve(fileName);  // 목적지 Path 생성
        System.out.println(destinationPath);
        Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
        //여기까지는 ok -> 이미지가 저장되는걸로 확인

        bgRepository.saveToDB(imageEntity);
    }

    public List<ImageEntity> findImagesById(String sessionAttribute) {
        return bgRepository.findById(sessionAttribute);}
}
