package com.timeline.controller;

import com.timeline.controller.dto.classes.like.ClassLikeRequestDto;
import com.timeline.controller.dto.classes.like.ClassLikeResponseDto;
import com.timeline.controller.dto.classes.like.ClassMasterLikeCountResponseDto;
import com.timeline.controller.dto.timeline.TimelineMasterLikeCountResponseDto;
import com.timeline.controller.dto.timeline.like.TimelineLikeRequestDto;
import com.timeline.controller.dto.timeline.like.TimelineLikeResponseDto;
import com.timeline.service.ClassLikeService;
import com.timeline.service.TimelineLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-06-16 오후 7:35
 * @brief : 클래스 추천 관리 컨트롤러
 **/
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/class")
@RequiredArgsConstructor
public class ClassLikeController {

    private final ClassLikeService classLikeService;

    /* 클래스 추천 혹은 취소할 시 */
    @PostMapping("/like/change")
    public ClassMasterLikeCountResponseDto changeLike(@RequestBody ClassLikeRequestDto classLikeRequestDto){
        return classLikeService.changeLike(classLikeRequestDto);
    }
//
//    /* 클래스 추천할 시 */
//    @PostMapping("/like")
//    public ClassMasterLikeCountResponseDto addLike(@RequestBody ClassLikeRequestDto classLikeRequestDto){
//        return classLikeService.addLike(classLikeRequestDto);
//    }
//
//    /* 클래스 추천 취소할 시 */
//    @DeleteMapping("/like")
//    public ClassMasterLikeCountResponseDto deleteLike(@RequestBody ClassLikeRequestDto classLikeRequestDto){
//        return classLikeService.deleteLike(classLikeRequestDto);
//    }

    /* 이미 추천한 클래스인지 확인 */
    @GetMapping("/like/check")
    public boolean checkLike(@RequestBody ClassLikeRequestDto classLikeRequestDto){
        return classLikeService.checkLike(classLikeRequestDto);
    }


    /* 한 클래스 게시물에 추천한 회원 조회 */
    @GetMapping("/like/list/masterId/{masterId}")
    public List<ClassLikeResponseDto> findLikeByMasterId(@PathVariable Long masterId){
        return classLikeService.findLikeByMasterId(masterId);
    }

    /* 한 회원이 추천한 클래스 조회 */
    @GetMapping("/like/list/memberId/{memberId}")
    public List<ClassLikeResponseDto> findLikeByMemberId(@PathVariable Long memberId){
        return classLikeService.findLikeByMemberId(memberId);
    }

    /* 전체 추천 테이블 조회 */
    @GetMapping("/like/list")
    public List<ClassLikeResponseDto> findLikes(){
        return classLikeService.findLikes();
    }


}
