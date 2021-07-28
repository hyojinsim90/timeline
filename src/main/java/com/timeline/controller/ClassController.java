package com.timeline.controller;

import com.timeline.controller.dto.classes.*;
import com.timeline.entity.classes.ClassDetail;
import com.timeline.service.ClassService;
import com.timeline.util.FileHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-07-20 오전 12:49
 * @brief :
 **/
@Slf4j
@CrossOrigin // 다른 포트에서의 요청 허용
@RestController
@RequestMapping("/class")
@RequiredArgsConstructor
public class ClassController {

    private final ClassService classService;
    private final FileHandler fileHandler;


    /* 전체 클래스 마스터 조회 */
    @GetMapping("/master/list")
    public List<ClassMasterResponseDto> findAllMaster() {
        return classService.findAllMaster();
    }

    /* 내 클래스 마스터 조회 */
    @GetMapping("/master/{email}")
    public ResponseEntity<List<ClassMasterResponseDto>> getMyclassMaster(@PathVariable String email) {
        return ResponseEntity.ok(classService.getMyclassMaster(email));
    }

    /* 추천수 top 10 조회 */
    @GetMapping("/master/list/like")
    public List<ClassMasterResponseDto> findMasterLike() {
        return classService.findMasterLike();
    }

    /* 클래스 분야별 조회 */
    @GetMapping("/category/{category}")
    public List<ClassMasterResponseDto> findMasterCategory(@PathVariable String category) {
        return classService.findMasterCategory(category);
    }

    /* 클래스 시작일시순 */
    @GetMapping("/master/regist")
    public List<ClassMasterResponseDto> findRegist() {
        return classService.findRegist();
    }

    /* 클래스 등록일시순 */
    @GetMapping("/master/latest")
    public List<ClassMasterResponseDto> findLatest() {
        return classService.findLatest();
    }

    /* 클래스 좋아요 갯수순(인기순) */
    @GetMapping("/master/like")
    public List<ClassMasterResponseDto> findLike() {
        return classService.findLike();
    }

    /* 클래스 검색 조회 */
    @GetMapping("/search")
    public List<ClassMasterResponseDto> search(@RequestParam(value="category",required = false) String category,
                                               @RequestParam(value="priceSorting",required = false) String priceSorting,
                                               @RequestParam(value="placeSorting",required = false) String placeSorting,
                                               @RequestParam(value="className",required = false) String keyword) {
        return classService.search(category, priceSorting, placeSorting, keyword);
    }

    /* 클래스 조회수 조회 */
    @GetMapping("/view/{id}")
    public int viewCount(@PathVariable Long id){
        return classService.viewCount(id);
    }

    /* 클래스 추천수 조회 */
    @GetMapping("/like/{id}")
    public int likeCount(@PathVariable Long id){
        return classService.likeCount(id);
    }

    /* 클래스 마스터 저장 */
    @PostMapping(path = "/master/save",consumes = {"multipart/form-data"})
    public ResponseEntity<ClassMasterResponseDto> saveMaster(
            @RequestPart(value="dto") ClassMasterSaveRequestDto classMasterSaveRequestDto,
            @RequestPart(value="file",required = true) MultipartFile file) throws Exception
    {
        log.info("[/master/save]");

        return ResponseEntity.ok(classService.saveMaster(classMasterSaveRequestDto, file));
    }

    /* 클래스 마스터 수정 */
    @PutMapping(path = "/master/{id}",consumes = {"multipart/form-data"})
    public ResponseEntity<ClassMasterResponseDto> updateMaster(
            @PathVariable Long id,
            @RequestPart(value="dto") ClassMasterUpdateRequestDto classMasterUpdateRequestDto,
            @RequestPart(value="file",required = false) MultipartFile file) throws Exception
    {
        return ResponseEntity.ok(classService.updateMaster(id, classMasterUpdateRequestDto, file));
    }

    /* 클래스 조회수 증가 */
    @PutMapping(path = "/view/{id}")
    public ClassMasterResponseDto updateView(@PathVariable Long id) {
        return classService.updateView(id);
    }

    /* 클래스 추천수 증가 */
    @PutMapping(path = "/like/{id}")
    public ClassMasterResponseDto updateLike(@PathVariable Long id) {
        return classService.updateLike(id);
    }

    /* 내 클래스 디테일 조회 */
    @GetMapping("/detail/{masterId}")
    public ResponseEntity<List<ClassDetail>> getMytimelieDetail(@PathVariable Long masterId) {
        return classService.getMytimelieDetail(masterId);
    }

    /* 클래스 최소가격 가져오기 */
    @GetMapping("/detail/price/{masterId}")
    public int minPrice(@PathVariable Long masterId) {
        return classService.minPrice(masterId);
    }

    /* 클래스 디테일 저장 */
    @PostMapping("/detail/save")
    public ResponseEntity<List<ClassDetail>> saveDetail(@RequestBody List<ClassDetailSaveRequestDto> classDetailList) {
        return classService.saveDetail(classDetailList);
    }

    /* 클래스 디테일 수정 */
    @PutMapping("/detail/{masterId}")
    public ResponseEntity updateDetail(@PathVariable Long masterId, @RequestBody List<ClassDetailSaveRequestDto> classDetailList) {
        log.info("[/detail/{masterId}]");
        return ResponseEntity.ok(classService.updateDetail(masterId, classDetailList));
    }

    /* 클래스 디테일 전체 삭제 */
    @DeleteMapping("detail/{masterId}")
    public void deleteDetail(@PathVariable Long masterId) {
        classService.deleteDetail(masterId);
    }

    /* 클래스 디테일 하나만 삭제 */
    @DeleteMapping("detail/one")
    public void deleteDetailOne(@RequestParam(value = "masterId") Long masterId, @RequestParam(value = "detailId") Long detailid) {
        classService.deleteDetailOne(masterId, detailid);
    }

    /* 클래스 마스터 이미지 삭제 */
    @DeleteMapping("master/image/{masterId}")
    public boolean deleteFilePath(@PathVariable Long masterId) throws Exception { return fileHandler.deleteFileOneClass(masterId); }

    /* 클래스 전체 삭제 */
    @DeleteMapping("/{masterId}")
    public void delete(@PathVariable Long masterId) throws Exception {
        classService.delete(masterId);
    }
//
//    /* 100개 넘어간 클래스 게시물 조회 */
//    @GetMapping("/like/list/best")
//    public List<ClassMasterResponseDto> findBestLikes(){
//        return classService.findBestLikes();
//    }
//

    /* 클래스 이미지 전체 조회 */
    @GetMapping("/master/image/list")
    public List<ClassPictureResponseDto> findAllImages(){
        return classService.findAllImages();
    }


    /* 클래스 이미지 하나만 조회 */
    @GetMapping("/master/image/{masterId}")
    public ClassPictureResponseDto findOneImage(@PathVariable Long masterId){
        return classService.findOneImage(masterId);
    }

}
