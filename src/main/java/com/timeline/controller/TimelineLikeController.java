package com.timeline.controller;

import com.timeline.controller.dto.timeline.TimelineMasterLikeCountResponseDto;
import com.timeline.controller.dto.timeline.like.TimelineLikeRequestDto;
import com.timeline.controller.dto.timeline.like.TimelineLikeResponseDto;
import com.timeline.service.TimelineLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-06-16 오후 7:35
 * @brief : 타임라인 추천 관리 컨트롤러
 **/
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/timeline")
@RequiredArgsConstructor
public class TimelineLikeController {

    private final TimelineLikeService timelineLikeService;

    /* 타임라인 추천 혹은 취소할 시 */
    @PostMapping("/like/change")
    public TimelineMasterLikeCountResponseDto changeLike(@RequestBody TimelineLikeRequestDto timelineLikeRequestDto){
        return timelineLikeService.changeLike(timelineLikeRequestDto);
    }

    /* 타임라인 추천할 시 */
    @PostMapping("/like")
    public TimelineMasterLikeCountResponseDto addLike(@RequestBody TimelineLikeRequestDto timelineLikeRequestDto){
        return timelineLikeService.addLike(timelineLikeRequestDto);
    }

    /* 타임라인 추천 취소할 시 */
    @DeleteMapping("/like")
    public TimelineMasterLikeCountResponseDto deleteLike(@RequestBody TimelineLikeRequestDto timelineLikeRequestDto){
        return timelineLikeService.deleteLike(timelineLikeRequestDto);
    }

    /* 이미 추천한 타임라인인지 확인 */
    @GetMapping("/like/check")
    public boolean checkLike(@RequestBody TimelineLikeRequestDto timelineLikeRequestDto){
        return timelineLikeService.checkLike(timelineLikeRequestDto);
    }


    /* 한 타임라인 게시물에 추천한 회원 조회 */
    @GetMapping("/like/list/masterId/{masterId}")
    public List<TimelineLikeResponseDto> findLikeByMasterId(@PathVariable Long masterId){
        return timelineLikeService.findLikeByMasterId(masterId);
    }

    /* 한 회원이 추천한 타임라인 조회 */
    @GetMapping("/like/list/memberId/{memberId}")
    public List<TimelineLikeResponseDto> findLikeByMemberId(@PathVariable Long memberId){
        return timelineLikeService.findLikeByMemberId(memberId);
    }

    /* 전체 추천 테이블 조회 */
    @GetMapping("/like/list")
    public List<TimelineLikeResponseDto> findLikes(){
        return timelineLikeService.findLikes();
    }


}
