package com.timeline.controller;

import com.timeline.controller.dto.timeline.*;
import com.timeline.entity.timeline.TimelineDetail;
import com.timeline.repository.TimelinePictureRepository;
import com.timeline.service.TimelineService;
import com.timeline.util.FileHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
    private final FileHandler fileHandler;


    /* 전체 타임라인 마스터 조회 */
    @GetMapping("/master/list")
    public List<TimelineMasterListResponseDto> findAllMaster() {
        return timelineService.findAllMaster();
    }

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

    /* 타임라인 분야별 조회 */
    @GetMapping("/category/{category}")
    public List<TimelineMasterListResponseDto> findMasterCategory(@PathVariable String category) {
        return timelineService.findMasterCategory(category);
    }

    /* 타임라인 검색 조회 */
    @GetMapping("/search")
    public List<TimelineMasterListResponseDto> search(@RequestParam(value="category") String category, @RequestParam(value="keyword") String keyword) {
        return timelineService.search(category, keyword);
    }


    /* 타임라인 마스터 저장 */
    @PostMapping(path = "/master/save",consumes = {"multipart/form-data"})
    public ResponseEntity<TimelineMasterListResponseDto> saveMaster(
            @RequestPart(value="dto") TimelineMasterSaveRequestDto timelineMasterSaveRequestDto,
            @RequestPart(value="file",required = false) MultipartFile file) throws Exception
    {
        log.info("[/master/save]");

        return ResponseEntity.ok(timelineService.saveMaster(timelineMasterSaveRequestDto, file));
    }

    /* 타임라인 마스터 수정 */
    @PutMapping(path = "/master/{id}",consumes = {"multipart/form-data"})
    public ResponseEntity<TimelineMasterListResponseDto> updateMaster(
            @PathVariable Long id,
            @RequestPart(value="dto") TimelineMasterUpdateRequestDto timelineMasterUpdateRequestDto,
            @RequestPart(value="file",required = false) MultipartFile file) throws Exception
    {
        return ResponseEntity.ok(timelineService.updateMaster(id, timelineMasterUpdateRequestDto, file));
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

    /* 타임라인 디테일 삭제 */
    @DeleteMapping("detail/{masterId}")
    public void deleteDetail(@PathVariable Long masterId) {
        timelineService.deleteDetail(masterId);
    }


    /* 타임라인 마스터 이미지 삭제 */
    @DeleteMapping("master/image/{masterId}")
    public boolean deleteFilePath(@PathVariable Long masterId) throws Exception { return fileHandler.deleteFileOne(masterId); }

    /* 타임라인 전체 삭제 */
    @DeleteMapping("/{masterId}")
    public void delete(@PathVariable Long masterId) throws Exception {
        timelineService.delete(masterId);
    }

    /* 100개 넘어간 타임라인 게시물 조회 */
    @GetMapping("/like/list/best")
    public List<TimelineMasterResponseDto> findBestLikes(){
        return timelineService.findBestLikes();
    }


    /* 타임라임 이미지 전체 조회 */
    @GetMapping("/master/image/list")
    public List<TimelinePictureResponseDto> findAllImages(){
        return timelineService.findAllImages();
    }


    /* 타임라임 이미지 하나만 조회 */
    @GetMapping("/master/image/{masterId}")
    public TimelinePictureResponseDto findOneImage(@PathVariable Long masterId){
        return timelineService.findOneImage(masterId);
    }

}
