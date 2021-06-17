package com.timeline.controller;

import com.timeline.controller.dto.timeline.TimelineMasterLikeCountResponseDto;
import com.timeline.controller.dto.timeline.like.TimelineLikeRequestDto;
import com.timeline.service.TimelineLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    /* 타임라인 추천할 시 */
    @PostMapping("/like")
    public TimelineMasterLikeCountResponseDto changeLike(@RequestBody TimelineLikeRequestDto timelineLikeRequestDto){
        return timelineLikeService.changeLike(timelineLikeRequestDto);
    }

    /* 타임라인 추천 취소할 시 */

    /* 한 회원이 추천한 타임라인 조회 */

    /* 한 타임라인 게시물에 추천한 회원 조회 */

    /* 전체 추천 테이블 조회 */

    /* 100개 넘어간 타임라인 게시물 조회 */



}