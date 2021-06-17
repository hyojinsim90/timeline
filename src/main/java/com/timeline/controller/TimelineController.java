package com.timeline.controller;

import com.timeline.controller.dto.member.MemberListResponseDto;
import com.timeline.controller.dto.member.MemberResponseDto;
import com.timeline.controller.dto.member.MemberUpdateRequestDto;
import com.timeline.controller.dto.timeline.*;
import com.timeline.controller.dto.timeline.like.TimelineLikeResponseDto;
import com.timeline.entity.TimelineDetail;
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
@CrossOrigin // 다른 포트에서의 요청 허용
@RestController
@RequestMapping("/timeline")
@RequiredArgsConstructor
public class TimelineController {

    private final TimelineService timelineService;
    private final S3Service s3Service;


    /* 전체 타임라인 마스터 조회 */
    @GetMapping("/master/list")
    public List<TimelineMasterListResponseDto> findAllMaster() {
        return timelineService.findAllMaster();
    }

    /* 타임라인 마스터 아이디별 조회 */

    /* 내 타임라인 마스터 조회 */
    @GetMapping("/master/{email}")
    public ResponseEntity<List<TimelineMasterListResponseDto>> getMytimelieMaster(@PathVariable String email) {
        return ResponseEntity.ok(timelineService.getMytimelieMaster(email));
    }

    /* 조회수 top 10 조회 */
    @GetMapping("/master/list/view")
    public List<TimelineMasterListResponseDto> findMasterView() {
        return timelineService.findMasterView();
    }

    /* 추천수 top 10 조회 */
    @GetMapping("/master/list/like")
    public List<TimelineMasterListResponseDto> findMasterLike() {
        return timelineService.findMasterLike();
    }

    /* 타임라인 마스터 저장 */
    @PostMapping(path = "/master/save",consumes = {"multipart/form-data"})
    public ResponseEntity<TimelineMasterListResponseDto> saveMaster(@RequestPart(value="dto") List<TimelineMasterSaveRequestDto> timelineMasterSaveRequestDto, @RequestPart(value="file",required = false) MultipartFile file) throws IOException {
        log.info("[/master/save]");

        /* 수정시 명심 !
        timelineMasterSaveRequestDto의 filePath : 기존 파일
        MultipartFile의 파일 : 업로드될, 수정될 파일
         */

        String imgPath = s3Service.upload(timelineMasterSaveRequestDto.get(0).getFilePath(), file);
        timelineMasterSaveRequestDto.get(0).setFilePath(imgPath);

        return ResponseEntity.ok(timelineService.saveMaster(timelineMasterSaveRequestDto.get(0)));
    }

    /* 타임라인 마스터 수정 */
    @PutMapping(path = "/master/{id}",consumes = {"multipart/form-data"})
    public ResponseEntity<TimelineMasterListResponseDto> updateMaster(@PathVariable Long id, @RequestPart(value="dto") List<TimelineMasterUpdateRequestDto> timelineMasterUpdateRequestDto, @RequestPart(value="file",required = false) MultipartFile file) throws IOException {

//        String imgPath = s3Service.upload(timelineMasterUpdateRequestDto.get(0).getFilePath(), file);
        String imgPath = s3Service.updateUpload(timelineMasterUpdateRequestDto.get(0).getFilePath(), file);
        timelineMasterUpdateRequestDto.get(0).setFilePath(imgPath);

        return ResponseEntity.ok(timelineService.updateMaster(id, timelineMasterUpdateRequestDto.get(0), file));
    }

    /* 내 타임라인 디테일 조회 */
    @GetMapping("/detail/{masterId}")
    public ResponseEntity<List<TimelineDetail>> getMytimelieDetail(@PathVariable Long masterId) {
        return timelineService.getMytimelieDetail(masterId);
    }

    /* 타임라인 디테일 저장 */
    @PostMapping("/detail/save")
    public ResponseEntity<List<TimelineDetail>> saveDetail(@RequestBody List<TimelineDetailSaveRequestDto> timelineDetailList) {
        return timelineService.saveDetail(timelineDetailList);
    }

    /* 타임라인 디테일 수정 */
    @PutMapping("/detail/{masterId}")
    public ResponseEntity updateDetail(@PathVariable Long masterId, @RequestBody List<TimelineDetailUpdateRequestDto> timelineDetailList) {
        log.info("[/detail/{masterId}]");
        return ResponseEntity.ok(timelineService.updateDetail(masterId, timelineDetailList));
    }

    /* 타임라인 마스터 이미지 삭제 */
    @DeleteMapping("master/{filePath}")
    public boolean deleteFilePath(@PathVariable String filePath) { return s3Service.delete(filePath); }

    /* 타임라인 디테일 삭제 */
    @DeleteMapping("detail/{masterId}")
    public void deleteDetail(@PathVariable Long masterId) {
        timelineService.deleteDetail(masterId);
    }

    /* 타임라인 전체 삭제 */
    @DeleteMapping("/{masterId}")
    public void delete(@PathVariable Long masterId) {
        timelineService.delete(masterId);
    }

    /* 100개 넘어간 타임라인 게시물 조회 */
    @GetMapping("/like/list/best")
    public List<TimelineMasterResponseDto> findBestLikes(){
        return timelineService.findBestLikes();
    }


}
