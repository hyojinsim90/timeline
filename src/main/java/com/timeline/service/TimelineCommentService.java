package com.timeline.service;

import com.timeline.controller.dto.timeline.comment.TimelineCommentRequestDto;
import com.timeline.controller.dto.timeline.comment.TimelineCommentResponseDto;
import com.timeline.entity.TimelineComment;
import com.timeline.entity.TimelineDetail;
import com.timeline.repository.TimelineCommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-06-16 오후 7:23
 * @brief : 타임라인 댓글 관련 서비스
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class TimelineCommentService {

    private final TimelineCommentRepository timelineCommentRepository;


    /* 타임라인 댓글 저장 */
    @Transactional
    public TimelineCommentResponseDto save(TimelineCommentRequestDto timelineCommentRequestDto) {
        log.info("[ timeline_comment 저장 ]");

        TimelineComment timelineComment = timelineCommentRequestDto.toTimelineComment();
        return new TimelineCommentResponseDto(timelineCommentRepository.save(timelineComment));
    }

    /* 타임라인 댓글 수정 */
    @Transactional
    public ResponseEntity update(TimelineCommentRequestDto timelineCommentRequestDto) {
        log.info("[ timeline_comment 수정 ]");

        TimelineComment timelineComment  = timelineCommentRepository.findExistOne(timelineCommentRequestDto.getMasterId(), timelineCommentRequestDto.getNickname());

        timelineComment.update(timelineCommentRequestDto.getMasterId(), timelineCommentRequestDto.getNickname(), timelineCommentRequestDto.getStar(), timelineCommentRequestDto.getContent());
        return ResponseEntity.ok(timelineComment);
    }

    /* 타임라인 댓글 삭제 */
    @Transactional
    public boolean delete(TimelineCommentRequestDto timelineCommentRequestDto) {
        log.info("[ timeline_comment 삭제 ]");

        TimelineComment timelineComment  = timelineCommentRepository.findExistOne(timelineCommentRequestDto.getMasterId(), timelineCommentRequestDto.getNickname());
        
        if(timelineComment != null ){
            log.info("- comment 삭제 처리 ");
            TimelineComment timelineCommentNew = timelineCommentRequestDto.toTimelineComment();
            timelineCommentRepository.delete(timelineCommentNew);
            return true;
        } else {
            log.info("- 삭제할 댓글 없음 ");
            return false;
        }
        

    }


}
