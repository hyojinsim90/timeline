package com.timeline.service;

import com.timeline.controller.dto.timeline.comment.TimelineCommentCheckRequestDto;
import com.timeline.controller.dto.timeline.comment.TimelineCommentRequestDto;
import com.timeline.controller.dto.timeline.comment.TimelineCommentResponseDto;
import com.timeline.entity.timeline.TimelineComment;
import com.timeline.repository.TimelineCommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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


    /* 한 타임라인 게시물의 댓글들 조회 */
    @Transactional(readOnly = true)
    public List<TimelineCommentResponseDto> findCommentByMasterId(Long masterId) {
        log.info("[ 한 timeline의 댓글들 조회 ]");

        return timelineCommentRepository.findByMasterIdOrderByCreatedDateAsc(masterId).stream()
                .map(TimelineCommentResponseDto::new)
                .collect(Collectors.toList());
    }

    /* 회원 한명의 댓글들 조회 */
    @Transactional(readOnly = true)
    public List<TimelineCommentResponseDto> findCommentByNickname(String nickname) {
        log.info("[ 회원 한명의 댓글들 조회 ]");

        return timelineCommentRepository.findByNicknameOrderByCreatedDateAsc(nickname).stream()
                .map(TimelineCommentResponseDto::new)
                .collect(Collectors.toList());
    }

    /* 한 게시물에 이미 작성한 회원인지 조회 */
    @Transactional(readOnly = true)
    public boolean checkComment(TimelineCommentCheckRequestDto timelineCommentCheckRequestDto) {
        log.info("[ 한 게시물에 이미 작성한 회원인지 조회  ]");

       TimelineComment timelineComment = timelineCommentRepository.findExistOne(timelineCommentCheckRequestDto.getMasterId(), timelineCommentCheckRequestDto.getNickname());
       if(timelineComment != null){
           return true;
       } else {
           return false;
       }
    }

    /* 댓글 전체 조회 */
    @Transactional(readOnly = true)
    public List<TimelineCommentResponseDto> findAllComments() {
        log.info("[ timeline_comment 전체 조회 ]");
        return timelineCommentRepository.findAllByOrderByCreatedDateAsc().stream()
                .map(TimelineCommentResponseDto::new)
                .collect(Collectors.toList());
    }

    /* 타임라인 댓글 저장 */
    @Transactional
    public TimelineCommentResponseDto save(TimelineCommentRequestDto timelineCommentRequestDto) {
        log.info("[ timeline_comment 저장 ]");

        TimelineComment timelineComment = timelineCommentRequestDto.toTimelineComment();
        return new TimelineCommentResponseDto(timelineCommentRepository.save(timelineComment));
    }

    /* 타임라인 댓글 수정 */
    @Transactional
    public ResponseEntity update(Long id, TimelineCommentRequestDto timelineCommentRequestDto) {
        log.info("[ timeline_comment 수정 ]");

//        TimelineComment timelineComment  = timelineCommentRepository.findExistOne(timelineCommentRequestDto.getMasterId(), timelineCommentRequestDto.getNickname());
        TimelineComment timelineComment = timelineCommentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("comment 정보가 없습니다. id =" + id));

        timelineComment.update(timelineCommentRequestDto.getNickname(), timelineCommentRequestDto.getStar(), timelineCommentRequestDto.getContent());
        return ResponseEntity.ok(timelineComment);
    }

    /* 타임라인 댓글 삭제 */
    @Transactional
    public boolean delete(Long id) {
        log.info("[ timeline_comment 삭제 ]");

//        TimelineComment timelineComment  = timelineCommentRepository.findExistOne(timelineCommentRequestDto.getMasterId(), timelineCommentRequestDto.getNickname());
        TimelineComment timelineComment = timelineCommentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("comment 정보가 없습니다. id =" + id));

        if(timelineComment != null ){
            log.info("- comment 삭제 처리 ");
            timelineCommentRepository.deleteById(id);
            return true;
        } else {
            log.info("- 삭제할 댓글 없음 ");
            return false;
        }
        

    }

}
