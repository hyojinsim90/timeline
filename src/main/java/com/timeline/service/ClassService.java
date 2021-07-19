package com.timeline.service;

import com.timeline.controller.dto.classes.ClassDetailSaveRequestDto;
import com.timeline.controller.dto.classes.ClassMasterResponseDto;
import com.timeline.controller.dto.classes.ClassMasterSaveRequestDto;
import com.timeline.controller.dto.timeline.*;
import com.timeline.entity.classes.ClassDetail;
import com.timeline.entity.classes.ClassMaster;
import com.timeline.entity.classes.ClassPicture;
import com.timeline.entity.timeline.TimelineDetail;
import com.timeline.entity.timeline.TimelineMaster;
import com.timeline.entity.timeline.TimelinePicture;
import com.timeline.repository.*;
import com.timeline.util.FileHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-07-20 오전 3:35
 * @brief :
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class ClassService {

    private final ClassMasterRepository classMasterRepository;
    private final ClassDetailRepository classDetailRepository;
    private final ClassPictureRepository classPictureRepository;
    private final FileHandler fileHandler;
//    private final S3Service s3Service;


    /* 전체 클래스 마스터 조회 */
    @Transactional(readOnly = true)
    public List<ClassMasterResponseDto> findAllMaster() {
        log.info("[ class_master 전체 조회 ]");

        return classMasterRepository.findAll().stream()
                .map(ClassMasterResponseDto::new)
                .collect(Collectors.toList());
    }

    /* 내 클래스 마스터 조회 */
    @Transactional(readOnly = true)
    public List<ClassMasterResponseDto> getMyclassMaster(String organizerEmail) {
        log.info("[ 내 class_master 조회 ]");

        return classMasterRepository.findByOrganizerEmail(organizerEmail).stream()
                .map(ClassMasterResponseDto::new)
                .collect(Collectors.toList());
    }

    /* 추천수 top 10 조회 */
    @Transactional(readOnly = true)
    public List<ClassMasterResponseDto> findMasterLike() {
        log.info("[ class_master 추천수 TOP 10 조회 ]");

        return classMasterRepository.findTop10ByOrderByLikeCountDesc().stream()
                .map(ClassMasterResponseDto::new)
                .collect(Collectors.toList());
    }

    /* 클래스 분야별 조회 */
    public List<ClassMasterResponseDto> findMasterCategory(String category) {
        log.info("[ class_master 분야별 조회 ]");

        return classMasterRepository.findByCategory(category).stream()
                .map(ClassMasterResponseDto::new)
                .collect(Collectors.toList());
    }

//    /* 클래스 검색 조회 */
//    public List<ClassMasterResponseDto> search(String category, String keyword) {
//        log.info("[ class search ]");
//
//        return classMasterRepository.searchByKeyword(category,'%'+keyword+'%').stream()
//                .map(ClassMasterResponseDto::new)
//                .collect(Collectors.toList());
//
//    }

    /* 클래스 마스터 저장 */
    @Transactional
    public ClassMasterResponseDto saveMaster(
            ClassMasterSaveRequestDto classMasterSaveRequestDto,
            MultipartFile file) throws Exception
    {
        log.info("[ class_master 저장 ]");

        // 1. 클래스 마스터 build
        ClassMaster classMaster = classMasterRepository.save(classMasterSaveRequestDto.toClassMaster());

        log.info("class_master_id : " + classMaster.getId());

        // 3. 파일을 저장하고 그 ClassPicture 에 대한 list 를 가지고 있는다
        ClassPicture classPicture = fileHandler.parseFileInfoOneClass(classMaster.getId(), file);

        // 4. classPicture 저장
        classPictureRepository.save(classPicture);

        // 5. classMaster 저장된 정보를 리턴함
        return new ClassMasterResponseDto(classMaster);

    }

//    /* 클래스 마스터 수정 */
//    @Transactional
//    public ClassMasterResponseDto updateMaster(Long id, ClassMasterUpdateRequestDto masterUpdateDto, MultipartFile file) throws Exception {
//        log.info("[ class_master 수정 ]");
//
//        // 파일 삭제 처리 : 파일 삭제 + ClassPicture 삭제
//        boolean deleteOk = fileHandler.deleteFileOne(id);
//
//        if(deleteOk){
//            // 새로 전달된 파일을 저장하고 그 ClassPicture 에 대한 list 를 가지고 있는다
//            ClassMaster master = classMasterRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Master 정보가 없습니다. id =" + id));
//            ClassPicture classPicture = fileHandler.parseFileInfoOne(master.getId(), file);
//
//            // ClassPicture 저장
//            classPictureRepository.save(classPicture);
//
//            // classMaster 업데이트
//            master.update(masterUpdateDto);
//
//            return new ClassMasterResponseDto(master);
//        }else {
//            return null;
//        }
//
//    }

    /* 내 클래스 디테일 조회 */
    @Transactional(readOnly = true)
    public ResponseEntity<List<ClassDetail>> getMytimelieDetail(Long masterId) {
        log.info("[ 내 class_detail 조회 ]");

        return ResponseEntity.ok(classDetailRepository.findByMasterId(masterId));
    }

    /* 클래스 디테일 저장 */
    @Transactional
    public ResponseEntity<List<ClassDetail>> saveDetail(List<ClassDetailSaveRequestDto> classDetailList) {
        log.info("[ class_detail 저장 ]");

        ClassDetail classDetail = null;
        List<ClassDetail> classDetails = new ArrayList<>();

        // 넘어온 리스트를 꺼내 날짜변환후 다시 저장
        for (int i = 0; i < classDetailList.size(); i++) {

            // class_detail 생성
            classDetail = classDetailList.get(i).toClassDetail();

            // detail 리스트에 객체 저장
            classDetails.add(classDetail);
        }

        // class_detail List전체 저장
        return ResponseEntity.ok(classDetailRepository.saveAll(classDetails));
    }
//
//    /* 클래스 디테일 수정 */
//    @Transactional
//    public ResponseEntity updateDetail(Long masterId, List<ClassDetailUpdateRequestDto> classDetailList) {
//        log.info("[ class_detail 수정 ]");
//
//        //  변수선언 : 날짜변환, detail entity, detail담을 리스트
//        List<ClassDetail> classDetails = new ArrayList<>();
//
//        // 넘어온 리스트를 꺼내 날짜변환후 다시 저장
//        for (int i = 0; i < classDetailList.size(); i++) {
//
//            // 클래스 마스터아이디, 디테일 아이디로 class_detail entity가져오기
//            ClassDetail classDetail = classDetailRepository.findDetail(masterId, classDetailList.get(i).getId());
//            if (classDetail == null){
//                new IllegalArgumentException(("timelne_detail 정보가 없습니다"));
//            }
//
//            // class_detail 저장
//            classDetail.update(classDetailList.get(i).getScheduleDate(), classDetailList.get(i).getTitle(), classDetailList.get(i).getContent());
//
//            // detail 리스트에 객체 저장
//            classDetails.add(classDetail);
//        }
//
////        log.info("[ classDetails ]" + classDetails);
//
//        return ResponseEntity.ok(classDetails);
//    }
//
//    /* 클래스 디테일 삭제 */
//    @Transactional
//    public void deleteDetail(Long masterId) {
//        List<ClassDetail> classDetail = classDetailRepository.findByMasterId(masterId);
//
//        log.info("classDetail.isEmpty() : " + classDetail.isEmpty());
//
//        if(!classDetail.isEmpty()){
//            for (int i = 0; i < classDetail.size(); i++) {
//                classDetailRepository.delete(classDetail.get(i));
//            }
//        }
//    }
//
//    /* 클래스 전체 삭제 */
//    @Transactional
//    public void delete(Long masterId) throws Exception {
//
//        ClassMaster classMaster = classMasterRepository.findById(masterId).orElseThrow(() -> new IllegalArgumentException("class_master 정보가 없습니다. masterId =" + masterId));
//        List<ClassDetail> classDetail = classDetailRepository.findByMasterId(masterId);
//
//        // 파일 삭제 처리 : 파일 삭제 + ClassPicture 삭제
//        Boolean deleteCk = fileHandler.deleteFileOne(masterId);
//        if(deleteCk){
//            // 마스터 삭제
//            classMasterRepository.delete(classMaster);
//
//            for (int i = 0; i < classDetail.size(); i++) {
//
//                // 디테일 삭제
//                classDetailRepository.delete(classDetail.get(i));
//            }
//        } else {
//            throw new Exception("파일이 삭제되지 않았습니다");
//        }
//
//    }
//
//    /* 100개 넘어간 클래스 게시물 조회 */
//    @Transactional(readOnly = true)
//    public List<ClassMasterResponseDto> findBestLikes() {
//        log.info("[ 100개 넘어간 추천 클래스 조회 ]");
//
//        return classMasterRepository.findBestLikes().stream()
//                .map(ClassMasterResponseDto::new)
//                .collect(Collectors.toList());
//    }
//
//    /* 클래스 이미지 전체 조회 */
//    public List<ClassPictureResponseDto> findAllImages() {
//        log.info("[ class_picture 전체 조회 ]");
//
//        return classPictureRepository.findAll().stream()
//                .map(ClassPictureResponseDto::new)
//                .collect(Collectors.toList());
//    }
//
//    /* 클래스 이미지 하나만 조회 */
//    public ClassPictureResponseDto findOneImage(Long masterId) {
//        log.info("[ 내 class_picture 조회 ]");
//
//        return new ClassPictureResponseDto(classPictureRepository.findByClassMasterId(masterId));
//    }
}
