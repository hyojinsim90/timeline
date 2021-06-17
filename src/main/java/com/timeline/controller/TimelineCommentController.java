package com.timeline.controller;

import com.timeline.controller.dto.timeline.TimelineMasterListResponseDto;
import com.timeline.controller.dto.timeline.comment.TimelineCommentCheckRequestDto;
import com.timeline.controller.dto.timeline.comment.TimelineCommentRequestDto;
import com.timeline.controller.dto.timeline.comment.TimelineCommentResponseDto;
import com.timeline.service.TimelineCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-06-16 오후 6:56
 * @brief : 타임라인 상세페이지의 댓글 관련 컨트롤러
 **/
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/timeline")
@RequiredArgsConstructor
public class TimelineCommentController {

    private final TimelineCommentService timelineCommentService;

    /* 한 타임라인 게시물의 댓글들 조회 */
    @GetMapping("/comment/list/{masterId}")
    public List<TimelineCommentResponseDto> findCommentByMasterId(@PathVariable Long masterId) {
        return timelineCommentService.findCommentByMasterId(masterId);
    }

    /* 회원 한명의 댓글들 조회 */
    @GetMapping("/comment/list/{nickname}")
    public List<TimelineCommentResponseDto> findCommentByNickname(@PathVariable String nickname) {
        return timelineCommentService.findCommentByNickname(nickname);
    }

    /* 한 게시물에 이미 작성한 회원인지 조회 */
    @GetMapping("/comment/check")
    public boolean checkComment(@RequestBody TimelineCommentCheckRequestDto timelineCommentCheckRequestDto) {
        return timelineCommentService.checkComment(timelineCommentCheckRequestDto);
    }

    /* 댓글 전체 조회 */
    @GetMapping("/comment/list")
    public List<TimelineCommentResponseDto> findAllComments() {
        return timelineCommentService.findAllComments();
    }

    /* 타임라인 댓글 저장 */
    @PostMapping(path = "/comment")
    public ResponseEntity<TimelineCommentResponseDto> save(@RequestBody TimelineCommentRequestDto timelineCommentRequestDto) {
        return ResponseEntity.ok(timelineCommentService.save(timelineCommentRequestDto));
    }

    /* 타임라인 댓글 수정 */
    @PutMapping(path = "/comment")
    public ResponseEntity update(@RequestBody TimelineCommentRequestDto timelineCommentRequestDto) {
        return ResponseEntity.ok(timelineCommentService.update(timelineCommentRequestDto));
    }

    /* 타임라인 댓글 삭제 */
    @DeleteMapping(path = "/comment")
    public boolean delete(@RequestBody TimelineCommentRequestDto timelineCommentRequestDto) {
        return timelineCommentService.delete(timelineCommentRequestDto);
    }



}
