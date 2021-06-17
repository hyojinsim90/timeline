package com.timeline.service;

import com.timeline.controller.dto.timeline.TimelineMasterLikeCountResponseDto;
import com.timeline.controller.dto.timeline.comment.TimelineCommentResponseDto;
import com.timeline.controller.dto.timeline.like.TimelineLikeRequestDto;
import com.timeline.controller.dto.timeline.like.TimelineLikeResponseDto;
import com.timeline.entity.TimelineLike;
import com.timeline.entity.TimelineMaster;
import com.timeline.repository.TimelineLikeRepository;
import com.timeline.repository.TimelineMasterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-06-16 오후 7:37
 * @brief : 타임라인 추천 관리 서비스
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class TimelineLikeService {

    private final TimelineLikeRepository timelineLikeRepository;
    private final TimelineMasterRepository timelineMasterRepository;

    /* 타임라인 추천할 시 */
    @Transactional
    public TimelineMasterLikeCountResponseDto addLike(TimelineLikeRequestDto timelineLikeRequestDto) {
        log.info("[ timeline_like 추천 처리 ]");

        // timeline_like 테이블에 저장
        timelineLikeRepository.save(timelineLikeRequestDto.toTimelineLike());

        // timeline_master의 추천 수도 올려줌
        TimelineMaster master = timelineMasterRepository.findById(timelineLikeRequestDto.getMasterId()).orElseThrow(() -> new IllegalArgumentException("Master 정보가 없습니다. id =" + timelineLikeRequestDto.getMasterId()));
        master.updateLikeCount(master.getLikeCount()+1);

        // 변경된 timeline_master의 추천수 반환
        return TimelineMasterLikeCountResponseDto.of(master);

    }

    /* 타임라인 추천 취소할 시 */
    @Transactional
    public TimelineMasterLikeCountResponseDto deleteLike(TimelineLikeRequestDto timelineLikeRequestDto) {
        log.info("[ timeline_like 추천 취소 처리 ]");

        // timeline_like 테이블에서 삭제
        timelineLikeRepository.delete(timelineLikeRequestDto.toTimelineLike());

        // timeline_master의 추천 수도 내려줌
        TimelineMaster master = timelineMasterRepository.findById(timelineLikeRequestDto.getMasterId()).orElseThrow(() -> new IllegalArgumentException("Master 정보가 없습니다. id =" + timelineLikeRequestDto.getMasterId()));
        master.updateLikeCount(master.getLikeCount()-1);

        // 변경된 timeline_master의 추천수 반환
        return TimelineMasterLikeCountResponseDto.of(master);

    }

    @Transactional
    public TimelineMasterLikeCountResponseDto changeLike(TimelineLikeRequestDto timelineLikeRequestDto) {
        log.info("[ timeline_like 추천 or 취소 처리 ]");

        // 중복인지 확인
        Boolean checkLike = checkLike(timelineLikeRequestDto);
        log.info("[ checkLike ]" + checkLike);


        if(!checkLike){
            log.info("- 추천된 적 없는 timeline ");
            // 중복이 아니면 timeline_like 테이블에 저장
            timelineLikeRepository.save(timelineLikeRequestDto.toTimelineLike());

            // timeline_master의 추천 수도 올려줌
            TimelineMaster master = timelineMasterRepository.findById(timelineLikeRequestDto.getMasterId()).orElseThrow(() -> new IllegalArgumentException("Master 정보가 없습니다. id =" + timelineLikeRequestDto.getMasterId()));
            master.updateLikeCount(master.getLikeCount()+1);

            // 변경된 timeline_master 반환
            return TimelineMasterLikeCountResponseDto.of(master);
        } else {
            // 중복이면 (이미 추천했으면) 추천 취소 처리
            log.info("- 이미 추천된 timeline -> 취소 처리 ");
            TimelineLike timelineLike = timelineLikeRepository.findExistOne(timelineLikeRequestDto.getMasterId(), timelineLikeRequestDto.getMasterId());

            log.info("[ timelineLike.getMemberId() ]" + timelineLike.getMemberId());

            // timeline_like 테이블에서 삭제
            timelineLikeRepository.delete(timelineLikeRequestDto.toTimelineLike());

            // timeline_master의 추천 수도 내려줌
            TimelineMaster master = timelineMasterRepository.findById(timelineLikeRequestDto.getMasterId()).orElseThrow(() -> new IllegalArgumentException("Master 정보가 없습니다. id =" + timelineLikeRequestDto.getMasterId()));
            master.updateLikeCount(master.getLikeCount()-1);

            // 변경된 timeline_master 반환
            return TimelineMasterLikeCountResponseDto.of(master);
        }
    }

    /* 이미 추천한 타임라인인지 확인 */
    @Transactional(readOnly = true)
    public boolean checkLike(TimelineLikeRequestDto timelineLikeRequestDto) {

        return timelineLikeRepository.checkLike(timelineLikeRequestDto.getMasterId(), timelineLikeRequestDto.getMemberId());

    }

    /* 한 타임라인 게시물에 추천한 회원 조회 */
    @Transactional(readOnly = true)
    public List<TimelineLikeResponseDto> findLikeByMasterId(Long masterId) {
        log.info("[ 한 timeline의 추천회원들 조회 ]");

        return timelineLikeRepository.findByMasterId(masterId).stream()
                .map(TimelineLikeResponseDto::new)
                .collect(Collectors.toList());
    }

    /* 한 회원이 추천한 타임라인 조회 */
    @Transactional(readOnly = true)
    public List<TimelineLikeResponseDto> findLikeByMemberId(Long memberId) {
        log.info("[ 한 회원의 추천 timeline 조회 ]");

        return timelineLikeRepository.findByMemberId(memberId).stream()
                .map(TimelineLikeResponseDto::new)
                .collect(Collectors.toList());

    }

    /* 전체 추천 테이블 조회 */
    @Transactional(readOnly = true)
    public List<TimelineLikeResponseDto> findLikes() {
        log.info("[ 전체 추천 조회 ]");

        return timelineLikeRepository.findAll().stream()
                .map(TimelineLikeResponseDto::new)
                .collect(Collectors.toList());
    }


}
