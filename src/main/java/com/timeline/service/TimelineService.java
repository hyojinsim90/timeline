package com.timeline.service;

import com.timeline.controller.dto.member.MemberListResponseDto;
import com.timeline.controller.dto.member.MemberUpdateRequestDto;
import com.timeline.controller.dto.timeline.*;
import com.timeline.entity.Member;
import com.timeline.entity.TimelineDetail;
import com.timeline.entity.TimelineMaster;
import com.timeline.repository.TimelineDetailRepository;
import com.timeline.repository.TimelineMasterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private S3Service s3Service;


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

    /* 타임라인 마스터 저장 */
    @Transactional
    public TimelineMasterListResponseDto saveMaster(TimelineMasterSaveRequestDto timelineMasterSaveRequestDto) throws IOException {
        log.info("[ timeline_master 저장 ]");

        TimelineMaster timelineMaster = timelineMasterSaveRequestDto.toTimelineMaster();

//        timelineMasterRepository.save(timelineMaster);

        return new TimelineMasterListResponseDto(timelineMasterRepository.save(timelineMaster));

    }

    /* 타임라인 마스터 수정 */
    @Transactional
    public TimelineMasterListResponseDto updateMaster(Long id, TimelineMasterUpdateRequestDto masterUpdateDto, MultipartFile file) throws IOException {
        log.info("[ timeline_master 수정 ]");

        TimelineMaster master = timelineMasterRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Master 정보가 없습니다. id =" + id));

        master.update(masterUpdateDto.getTitle(), masterUpdateDto.getFilePath(), masterUpdateDto.getCategory(), masterUpdateDto.isOpen(), masterUpdateDto.isComplete());

        log.info("[ master ] " + master );

        return new TimelineMasterListResponseDto(master);

    }

    /* 타임라인 디테일 저장 */
    @Transactional
    public ResponseEntity<List<TimelineDetail>> saveDetail(List<TimelineDetailSaveRequestDto> timelineDetailList) {
        log.info("[ timeline_detail 저장 ]");

        //  변수선언 : 날짜변환, detail entity, detail담을 리스트
        LocalDate scheduleDate = null;
        String beforeDate = "";
        String afterDate = "";
        TimelineDetail timelineDetail = null;
        List<TimelineDetail> timelineDetails = new ArrayList<>();

        // 넘어온 리스트를 꺼내 날짜변환후 다시 저장
        for (int i = 0; i < timelineDetailList.size(); i++) {
            // 날짜 변환
            beforeDate = timelineDetailList.get(i).getScheduleDate().toString();
            afterDate = beforeDate.substring(0, 4)+"-"+beforeDate.substring(4, 6)+"-"+beforeDate.substring(6, 8);
            scheduleDate = LocalDate.parse(afterDate, DateTimeFormatter.ISO_DATE);
//            log.info("[ beforeDate ]" + beforeDate);
//            log.info("[ afterDate ]" + afterDate);
            log.info("[ scheduleDate ]" + scheduleDate);

            // timeline_detail 생성
            timelineDetail = timelineDetailList.get(i).toTimelineDetail(scheduleDate);

            // detail 리스트에 객체 저장
            timelineDetails.add(timelineDetail);
        }
//        log.info("[ timelineDetails ]" + timelineDetails);

        // timeline_detail List전체 저장
        return ResponseEntity.ok(timelineDetailRepository.saveAll(timelineDetails));
    }

    /* 타임라인 디테일 수정 */
    @Transactional
    public Object updateDetail(Long masterId, List<TimelineDetailUpdateRequestDto> timelineDetailList) {
        log.info("[ timeline_detail 수정 ]");

        //  변수선언 : 날짜변환, detail entity, detail담을 리스트
        List<TimelineDetail> timelineDetails = new ArrayList<>();
        LocalDate scheduleDate = null;
        String beforeDate = "";
        String afterDate = "";

        // 넘어온 리스트를 꺼내 날짜변환후 다시 저장
        for (int i = 0; i < timelineDetailList.size(); i++) {
            // 날짜 변환
            beforeDate = timelineDetailList.get(i).getScheduleDate().toString();
            afterDate = beforeDate.substring(0, 4)+"-"+beforeDate.substring(4, 6)+"-"+beforeDate.substring(6, 8);
            scheduleDate = LocalDate.parse(afterDate, DateTimeFormatter.ISO_DATE);
            log.info("[ scheduleDate ]" + scheduleDate);

            // 타임라인 마스터아이디, 디테일 아이디로 timeline_detail entity가져오기
            TimelineDetail timelineDetail = timelineDetailRepository.findDetail(masterId, timelineDetailList.get(i).getId());
            if (timelineDetail == null){
                new IllegalArgumentException(("timelne_detail 정보가 없습니다"));
            }

            // timeline_detail 저장
            timelineDetail.update(scheduleDate, timelineDetailList.get(i).getTitle(), timelineDetailList.get(i).getContent());

            // detail 리스트에 객체 저장
            timelineDetails.add(timelineDetail);
        }

//        log.info("[ timelineDetails ]" + timelineDetails);

        return ResponseEntity.ok(timelineDetails);
    }

}
