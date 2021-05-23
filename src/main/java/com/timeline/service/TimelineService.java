package com.timeline.service;

import com.timeline.controller.dto.timeline.TimelineDetailSaveRequestDto;
import com.timeline.controller.dto.timeline.TimelineMasterResponseDto;
import com.timeline.controller.dto.timeline.TimelineMasterSaveRequestDto;
import com.timeline.entity.TimelineDetail;
import com.timeline.entity.TimelineMaster;
import com.timeline.repository.TimelineDetailRepository;
import com.timeline.repository.TimelineMasterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    @Transactional
    public TimelineMasterResponseDto saveMaster(TimelineMasterSaveRequestDto timelineMasterSaveRequestDto){
        log.info("[ timeline_master 저장 ]");

        TimelineMaster timelineMaster = timelineMasterSaveRequestDto.toTimelineMaster();

        timelineMasterRepository.save(timelineMaster);

        return TimelineMasterResponseDto.of(timelineMasterRepository.save(timelineMaster));

    }

    public ResponseEntity<List<TimelineDetail>> saveDetail(List<TimelineDetailSaveRequestDto> timelineDetailList) {
        log.info("[ timeline_detail 저장 ]");

        // id 꺼내서 master에 있는지 확인하고 없으면 에러처리

        List<TimelineDetail> timelineDetails = new ArrayList<>();
        LocalDate scheduleDate = null;
        String beforeDate = "";
        String afterDate = "";
        TimelineDetail timelineDetail = null;

        for (int i = 0; i < timelineDetailList.size(); i++) {
            beforeDate = timelineDetailList.get(i).getScheduleDate().toString();
            log.info("[ beforeDate ]" + beforeDate);
            afterDate = beforeDate.substring(0, 4)+"-"+beforeDate.substring(4, 6)+"-"+beforeDate.substring(6, 8);
            log.info("[ afterDate ]" + afterDate);
            scheduleDate = LocalDate.parse(afterDate, DateTimeFormatter.ISO_DATE);
            log.info("[ scheduleDate ]" + scheduleDate);
            timelineDetail = timelineDetailList.get(i).toTimelineDetail(scheduleDate);
            log.info("[ timelineDetail ]" + timelineDetail);
            timelineDetails.add(timelineDetail);
        }
        log.info("[ timelineDetails ]" + timelineDetails);

        return ResponseEntity.ok(timelineDetailRepository.saveAll(timelineDetails));
    }
}
