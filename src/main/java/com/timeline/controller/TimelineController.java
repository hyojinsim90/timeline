package com.timeline.controller;

import com.timeline.controller.dto.member.MemberListResponseDto;
import com.timeline.controller.dto.member.MemberResponseDto;
import com.timeline.controller.dto.member.MemberUpdateRequestDto;
import com.timeline.controller.dto.timeline.*;
import com.timeline.service.S3Service;
import com.timeline.service.TimelineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    /* 타임라인 마스터 저장 */
    @PostMapping("/master/save")
    public ResponseEntity<TimelineMasterResponseDto> saveMaster(@RequestBody TimelineMasterSaveRequestDto timelineMasterSaveRequestDto, MultipartFile file) throws IOException {
//        String imgPath = s3Service.upload(file);

        return ResponseEntity.ok(timelineService.saveMaster(timelineMasterSaveRequestDto, file));
    }

    /* 타임라인 디테일 저장 */
    @PostMapping("/detail/save")
    public ResponseEntity saveDetail(@RequestBody List<TimelineDetailSaveRequestDto> timelineDetailList) {
        return timelineService.saveDetail(timelineDetailList);
    }

    /* 전체 타임라인 마스터 정보 가져옴 */
    @GetMapping("/master/list")
    public List<TimelineMasterListResponseDto> findAllMaster() {
        return timelineService.findAllMaster();
    }

    /* 타임라인 마스터 수정 */
    @PutMapping("/master/{id}")
    public ResponseEntity<TimelineMasterListResponseDto> updateMaster(@PathVariable Long id, @RequestBody TimelineMasterUpdateRequestDto timelineMasterUpdateRequestDto, MultipartFile file) throws IOException {
        return ResponseEntity.ok(timelineService.updateMaster(id, timelineMasterUpdateRequestDto, file));
    }

    /* 타임라인 디테일 수정 */
    @PutMapping("/detail/{id}")
    public ResponseEntity updateDetail(@PathVariable Long masterId, @RequestBody List<TimelineDetailUpdateRequestDto> timelineDetailList) {
        return ResponseEntity.ok(timelineService.updateDetail(masterId, timelineDetailList));
    }



}
