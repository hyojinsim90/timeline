package com.timeline.service;

import com.timeline.controller.dto.timeline.*;
import com.timeline.entity.timeline.TimelineDetail;
import com.timeline.entity.timeline.TimelineMaster;
import com.timeline.entity.timeline.TimelinePicture;
import com.timeline.repository.TimelineDetailRepository;
import com.timeline.repository.TimelineMasterRepository;
import com.timeline.repository.TimelinePictureRepository;
import com.timeline.util.FileHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-05-21 오후 4:34
 * @brief : 타임라인 관련 서비스
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class TimelineService {

    private final TimelineMasterRepository timelineMasterRepository;
    private final TimelineDetailRepository timelineDetailRepository;
    private final TimelinePictureRepository timelinePictureRepository;
    private final FileHandler fileHandler;
//    private final S3Service s3Service;


    /* 전체 타임라인 마스터 조회 */
    @Transactional(readOnly = true)
    public List<TimelineMasterListResponseDto> findAllMaster() {
        log.info("[ timeline_master 전체 조회 ]");

        return timelineMasterRepository.findAll().stream()
                .map(TimelineMasterListResponseDto::new)
                .collect(Collectors.toList());
    }

    /* 내 타임라인 마스터 조회 */
    @Transactional(readOnly = true)
    public List<TimelineMasterListResponseDto> getMytimelieMaster(String author) {
        log.info("[ 내 timeline_master 조회 ]");

        return timelineMasterRepository.findByAuthor(author).stream()
                .map(TimelineMasterListResponseDto::new)
                .collect(Collectors.toList());
    }

    /* 조회수 top 10 조회 */
    @Transactional(readOnly = true)
    public List<TimelineMasterListResponseDto> findMasterView() {
        log.info("[ timeline_master 조회수 TOP 10 조회 ]");

        return timelineMasterRepository.findTop10ByOrderByViewCountDesc().stream()
                .map(TimelineMasterListResponseDto::new)
                .collect(Collectors.toList());
    }

    /* 추천수 top 10 조회 */
    @Transactional(readOnly = true)
    public List<TimelineMasterListResponseDto> findMasterLike() {
        log.info("[ timeline_master 추천수 TOP 10 조회 ]");

        return timelineMasterRepository.findTop10ByOrderByLikeCountDesc().stream()
                .map(TimelineMasterListResponseDto::new)
                .collect(Collectors.toList());
    }

    /* 타임라인 분야별 조회 */
    public List<TimelineMasterListResponseDto> findMasterCategory(String category) {
        log.info("[ timeline_master 분야별 조회 ]");

        return timelineMasterRepository.findByCategory(category).stream()
                .map(TimelineMasterListResponseDto::new)
                .collect(Collectors.toList());
    }

    /* 타임라인 검색 조회 */
    public List<TimelineMasterListResponseDto> search(String category, String keyword) {
        log.info("[ timeline search ]");

        return timelineMasterRepository.searchByKeyword(category,'%'+keyword+'%').stream()
                .map(TimelineMasterListResponseDto::new)
                .collect(Collectors.toList());

    }

    /* 타임라인 마스터 저장 */
    @Transactional
    public TimelineMasterListResponseDto saveMaster(
            TimelineMasterSaveRequestDto timelineMasterSaveRequestDto,
            MultipartFile file) throws Exception
    {
        log.info("[ timeline_master 저장 ]");

        // 1. 타임라인 마스터 build
        TimelineMaster timelineMaster = timelineMasterRepository.save(timelineMasterSaveRequestDto.toTimelineMaster());

        log.info("master_id : " + timelineMaster.getId());

        // 3. 파일을 저장하고 그 TimelinePicture 에 대한 list 를 가지고 있는다
        TimelinePicture timelinePicture = fileHandler.parseFileInfoOne(timelineMaster.getId(), file);

        // 4. TimelinePicture 저장
        timelinePictureRepository.save(timelinePicture);

        // 5. timelineMaster 저장된 정보를 리턴함
        return new TimelineMasterListResponseDto(timelineMasterRepository.save(timelineMaster));

    }

    /* 타임라인 마스터 수정 */
    @Transactional
    public TimelineMasterListResponseDto updateMaster(Long id, TimelineMasterUpdateRequestDto masterUpdateDto, MultipartFile file) throws Exception {
        log.info("[ timeline_master 수정 ]");

        // 파일 삭제 처리 : 파일 삭제 + TimelinePicture 삭제
        boolean deleteOk = fileHandler.deleteFileOne(id);

        if(deleteOk){
            // 새로 전달된 파일을 저장하고 그 TimelinePicture 에 대한 list 를 가지고 있는다
            TimelineMaster master = timelineMasterRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Master 정보가 없습니다. id =" + id));
            TimelinePicture timelinePicture = fileHandler.parseFileInfoOne(master.getId(), file);

            // TimelinePicture 저장
            timelinePictureRepository.save(timelinePicture);

            // timelineMaster 업데이트
            master.update(masterUpdateDto.getTitle(), masterUpdateDto.getCategory(), masterUpdateDto.isOpen(), masterUpdateDto.isComplete());

            return new TimelineMasterListResponseDto(master);
        }else {
            return null;
        }



    }

    /* 내 타임라인 디테일 조회 */
    @Transactional(readOnly = true)
    public ResponseEntity<List<TimelineDetail>> getMytimelieDetail(Long masterId) {
        log.info("[ 내 timeline_detail 조회 ]");

        return ResponseEntity.ok(timelineDetailRepository.findByMasterId(masterId));
    }

    /* 타임라인 디테일 저장 */
    @Transactional
    public ResponseEntity<List<TimelineDetail>> saveDetail(List<TimelineDetailSaveRequestDto> timelineDetailList) {
        log.info("[ timeline_detail 저장 ]");

        //  변수선언 : 날짜변환, detail entity, detail담을 리스트
//        LocalDate scheduleDate = null;
//        String beforeDate = "";
//        String afterDate = "";
        TimelineDetail timelineDetail = null;
        List<TimelineDetail> timelineDetails = new ArrayList<>();

        // 넘어온 리스트를 꺼내 날짜변환후 다시 저장
        for (int i = 0; i < timelineDetailList.size(); i++) {
            // 날짜 변환
//            beforeDate = timelineDetailList.get(i).getScheduleDate().toString();
//            afterDate = beforeDate.substring(0, 4)+"-"+beforeDate.substring(4, 6)+"-"+beforeDate.substring(6, 8);
//            scheduleDate = LocalDate.parse(afterDate, DateTimeFormatter.ISO_DATE);
//            log.info("[ beforeDate ]" + beforeDate);
//            log.info("[ afterDate ]" + afterDate);
//            log.info("[ scheduleDate ]" + scheduleDate);

            // timeline_detail 생성
            timelineDetail = timelineDetailList.get(i).toTimelineDetail();

            // detail 리스트에 객체 저장
            timelineDetails.add(timelineDetail);
        }
//        log.info("[ timelineDetails ]" + timelineDetails);

        // timeline_detail List전체 저장
        return ResponseEntity.ok(timelineDetailRepository.saveAll(timelineDetails));
    }

    /* 타임라인 디테일 수정 */
    @Transactional
    public ResponseEntity updateDetail(Long masterId, List<TimelineDetailUpdateRequestDto> timelineDetailList) {
        log.info("[ timeline_detail 수정 ]");

        //  변수선언 : 날짜변환, detail entity, detail담을 리스트
        List<TimelineDetail> timelineDetails = new ArrayList<>();

        // 넘어온 리스트를 꺼내 날짜변환후 다시 저장
        for (int i = 0; i < timelineDetailList.size(); i++) {

            // 타임라인 마스터아이디, 디테일 아이디로 timeline_detail entity가져오기
            TimelineDetail timelineDetail = timelineDetailRepository.findDetail(masterId, timelineDetailList.get(i).getId());
            if (timelineDetail == null){
                new IllegalArgumentException(("timelne_detail 정보가 없습니다"));
            }

            // timeline_detail 저장
            timelineDetail.update(timelineDetailList.get(i).getScheduleDate(), timelineDetailList.get(i).getTitle(), timelineDetailList.get(i).getContent());

            // detail 리스트에 객체 저장
            timelineDetails.add(timelineDetail);
        }

//        log.info("[ timelineDetails ]" + timelineDetails);

        return ResponseEntity.ok(timelineDetails);
    }

    /* 타임라인 디테일 삭제 */
    @Transactional
    public void deleteDetail(Long masterId) {
        List<TimelineDetail> timelineDetail = timelineDetailRepository.findByMasterId(masterId);

        log.info("timelineDetail.isEmpty() : " + timelineDetail.isEmpty());

        if(!timelineDetail.isEmpty()){
            for (int i = 0; i < timelineDetail.size(); i++) {
                timelineDetailRepository.delete(timelineDetail.get(i));
            }
        }
    }

    /* 타임라인 전체 삭제 */
    @Transactional
    public void delete(Long masterId) throws Exception {

        TimelineMaster timelineMaster = timelineMasterRepository.findById(masterId).orElseThrow(() -> new IllegalArgumentException("timeline_master 정보가 없습니다. masterId =" + masterId));
        List<TimelineDetail> timelineDetail = timelineDetailRepository.findByMasterId(masterId);

        // 파일 삭제 처리 : 파일 삭제 + TimelinePicture 삭제
        Boolean deleteCk = fileHandler.deleteFileOne(masterId);
        if(deleteCk){
            // 마스터 삭제
            timelineMasterRepository.delete(timelineMaster);

            for (int i = 0; i < timelineDetail.size(); i++) {

                // 디테일 삭제
                timelineDetailRepository.delete(timelineDetail.get(i));
            }
        } else {
            throw new Exception("파일이 삭제되지 않았습니다");
        }

    }

    /* 100개 넘어간 타임라인 게시물 조회 */
    @Transactional(readOnly = true)
    public List<TimelineMasterResponseDto> findBestLikes() {
        log.info("[ 100개 넘어간 추천 타임라인 조회 ]");

        return timelineMasterRepository.findBestLikes().stream()
                .map(TimelineMasterResponseDto::new)
                .collect(Collectors.toList());
    }


}
