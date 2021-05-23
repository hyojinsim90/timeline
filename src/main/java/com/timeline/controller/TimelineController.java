package com.timeline.controller;

import com.timeline.controller.dto.member.MemberResponseDto;
import com.timeline.controller.dto.timeline.TimelineDetailSaveRequestDto;
import com.timeline.controller.dto.timeline.TimelineMasterResponseDto;
import com.timeline.controller.dto.timeline.TimelineMasterSaveRequestDto;
import com.timeline.service.TimelineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-05-21 오전 3:56
 * @brief : 타임라인 관련 컨트롤러
 **/
@Slf4j
@RestController
@RequestMapping("/timeline")
@RequiredArgsConstructor
public class TimelineController {

    private final TimelineService timelineService;

    @PostMapping("/master/save")
    public ResponseEntity<TimelineMasterResponseDto> saveMaster(@RequestBody TimelineMasterSaveRequestDto timelineMasterSaveRequestDto) {
        return ResponseEntity.ok(timelineService.saveMaster(timelineMasterSaveRequestDto));
    }

    @PostMapping("/detail/save")
    public ResponseEntity saveDetail(@RequestBody List<TimelineDetailSaveRequestDto> timelineDetailList) {
        return timelineService.saveDetail(timelineDetailList);
    }



}
