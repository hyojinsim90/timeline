package com.timeline.util;

import com.timeline.entity.timeline.TimelinePicture;
import com.timeline.repository.TimelinePictureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-07-12 오전 10:51
 * @brief : 이미지 파일에 대한 처리 클래스
 **/
@Component
@Slf4j
@RequiredArgsConstructor
public class FileHandler {

    private final TimelinePictureRepository timelinePictureRepository;

    /* 파일 하나 저장 */
    public TimelinePicture parseFileInfoOne(
            Long timelineID,
            MultipartFile multipartFile
    ) throws Exception {

        // 반환을 할 파일 정보 클래스
        TimelinePicture timelinePicture = new TimelinePicture();

        // 프로젝트 폴더에 저장하기 위해 절대경로를 설정 (Window 의 Tomcat 은 Temp 파일을 이용한다)
        String absolutePath = new File("").getAbsolutePath();
        log.info("absolutePath :  " + absolutePath);


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String current_date = simpleDateFormat.format(new Date());

        // 경로를 지정하고 그곳에다가 저장할 심산이다
        // 서버용
        String path = absolutePath + "/tiltest3/src/main/resources/image/" + current_date;
        // /tiltest3/src/main/resources/image/20210712

        File file = new File(path);
        // 저장할 위치의 디렉토리가 존지하지 않을 경우
        if (!file.exists()) {
            // mkdir() 함수와 다른 점은 상위 디렉토리가 존재하지 않을 때 그것까지 생성
            file.mkdirs();
        }

        // 파일이 빈 것이 들어오면 기존 이미지로 저장
        if (multipartFile.isEmpty()) {

            // 서버용
            File uploadfile = new File(path + "/timeline.jpg");

            // 로컬테스트용
//             multipartFile = (MultipartFile) new File(absolutePath + "\\src\\main\\resources\\image\\timeline.jpg");

            // 고유한 key값을 갖기 위해 현재 시간을 postfix로 붙여줌
            SimpleDateFormat date = new SimpleDateFormat("yyyymmddHHmmss");
            String newfileName = date.format(new Date()) + "-" + uploadfile.getName();

            // 생성 후 리스트에 추가
            timelinePicture = TimelinePicture.builder()
                    .timelineMasterId(timelineID)
                    .originalFileName(uploadfile.getName())
                    .storedFilePath(path + "/" + newfileName)
                    .fileSize(multipartFile.getSize())
                    .build();

            // 저장된 파일로 변경하여 이를 보여주기 위함
            File newFile = new File(path + "/" + newfileName);
            multipartFile.transferTo(newFile);

            return timelinePicture;

        } else {

            // 넘어온 파일이 있을 때

            // 고유한 key값을 갖기 위해 현재 시간을 postfix로 붙여줌
            SimpleDateFormat date = new SimpleDateFormat("yyyymmddHHmmss");
            String newfileName = date.format(new Date()) + "-" + multipartFile.getOriginalFilename();

            // 생성 후 리스트에 추가
            timelinePicture = TimelinePicture.builder()
                    .timelineMasterId(timelineID)
                    .originalFileName(multipartFile.getOriginalFilename())
                    .storedFilePath(path + "/" + newfileName)
                    .fileSize(multipartFile.getSize())
                    .build();

            // 저장된 파일로 변경하여 이를 보여주기 위함
            file = new File(absolutePath + path + "/" + newfileName);
            multipartFile.transferTo(file);

            return timelinePicture;

        }
    }

    /* 파일들 저장
    * List<MultipartFile>을 받아 사진을 저장하고 이에 대한 정보를 List<TimelinePicture>로 변경하여 반환
    * */
    public List<TimelinePicture> parseFileInfo(
            Long timelineID,
            List<MultipartFile> multipartFiles
    ) throws Exception {

        // 반환을 할 파일 리스트
        List<TimelinePicture> fileList = new ArrayList<>();

        // 파일이 빈 것이 들어오면 빈 것을 반환
        if (multipartFiles.isEmpty()) {
            return fileList;
        }

        // 파일 이름을 업로드 한 날짜로 바꾸어서 저장할 것이다
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String current_date = simpleDateFormat.format(new Date());

        // 프로젝트 폴더에 저장하기 위해 절대경로를 설정 (Window 의 Tomcat 은 Temp 파일을 이용한다)
        String absolutePath = new File("").getAbsolutePath() + "\\";

        // 경로를 지정하고 그곳에다가 저장할 심산이다
        String path = "images/" + current_date;

        File file = new File(path);
        // 저장할 위치의 디렉토리가 존지하지 않을 경우
        if (!file.exists()) {
            // mkdir() 함수와 다른 점은 상위 디렉토리가 존재하지 않을 때 그것까지 생성
            file.mkdirs();
        }

        // 파일들을 이제 만져볼 것이다
        for (MultipartFile multipartFile : multipartFiles) {
            // 파일이 비어 있지 않을 때 작업을 시작해야 오류가 나지 않는다
            if (!multipartFile.isEmpty()) {
                // jpeg, png, gif 파일들만 받아서 처리할 예정
                String contentType = multipartFile.getContentType();
                String originalFileExtension;
                // 확장자 명이 없으면 이 파일은 잘 못 된 것이다
                if (ObjectUtils.isEmpty(contentType)) {
                    break;
                } else {
                    if (contentType.contains("image/jpeg")) {
                        originalFileExtension = ".jpg";
                    } else if (contentType.contains("image/png")) {
                        originalFileExtension = ".png";
                    } else if (contentType.contains("image/gif")) {
                        originalFileExtension = ".gif";
                    }
                    // 다른 파일 명이면 아무 일 하지 않는다
                    else {
                        break;
                    }
                }
                // 각 이름은 겹치면 안되므로 나노 초까지 동원하여 지정
                String new_file_name = Long.toString(System.nanoTime()) + originalFileExtension;
                // 생성 후 리스트에 추가
                TimelinePicture timelinePicture = TimelinePicture.builder()
                        .timelineMasterId(timelineID)
                        .originalFileName(multipartFile.getOriginalFilename())
                        .storedFilePath(path + "/" + new_file_name)
                        .fileSize(multipartFile.getSize())
                        .build();
                fileList.add(timelinePicture);

                // 저장된 파일로 변경하여 이를 보여주기 위함
                file = new File(absolutePath + path + "/" + new_file_name);
                multipartFile.transferTo(file);
            }
        }

        return fileList;

    }

    /* 파일 하나 삭제 */
    public boolean deleteFileOne(
            Long id
    ) throws Exception {

        boolean checkDelete = false;

        // master_id로 TimelinePicture리턴
        TimelinePicture picture = timelinePictureRepository.findAllByTimelineIdx(id);

        // 실제 저장된 위치 삭제
        File storedfile = new File(picture.getStoredFilePath());
        if(storedfile.delete()){
            System.out.println("파일삭제 성공");
            // TimelinePicture 삭제
            timelinePictureRepository.delete(picture);
            checkDelete = true;
            return checkDelete;
        } else {
            System.out.println("파일삭제 실패");
            return checkDelete;
        }


    }
}
