package com.timeline.service;

import com.timeline.controller.dto.classes.like.ClassLikeRequestDto;
import com.timeline.controller.dto.classes.like.ClassLikeResponseDto;
import com.timeline.controller.dto.classes.like.ClassMasterLikeCountResponseDto;
import com.timeline.controller.dto.timeline.TimelineMasterLikeCountResponseDto;
import com.timeline.controller.dto.timeline.like.TimelineLikeRequestDto;
import com.timeline.controller.dto.timeline.like.TimelineLikeResponseDto;
import com.timeline.entity.classes.ClassMaster;
import com.timeline.entity.timeline.TimelineMaster;
import com.timeline.repository.ClassLikeRepository;
import com.timeline.repository.ClassMasterRepository;
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
 * @brief : 클래스 추천 관리 서비스
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class ClassLikeService {

    private final ClassLikeRepository classLikeRepository;
    private final ClassMasterRepository classMasterRepository;

//    /* 클래스 추천할 시 */
//    @Transactional
//    public ClassMasterLikeCountResponseDto addLike(ClassLikeRequestDto classLikeRequestDto) {
//        log.info("[ class_like 추천 처리 ]");
//
//        // class_like 테이블에 저장
//        classLikeRepository.save(classLikeRequestDto.toClassLike());
//
//        // class_master의 추천 수도 올려줌
//        ClassMaster master = classMasterRepository.findById(classLikeRequestDto.getMasterId()).orElseThrow(() -> new IllegalArgumentException("Master 정보가 없습니다. id =" + classLikeRequestDto.getMasterId()));
//        master.updateLikeCount(master.getLikeCount()+1);
//
//        // 변경된 class_master의 추천수 반환
//        return ClassMasterLikeCountResponseDto.of(master);
//
//    }
//
//    /* 클래스 추천 취소할 시 */
//    @Transactional
//    public ClassMasterLikeCountResponseDto deleteLike(ClassLikeRequestDto classLikeRequestDto) {
//        log.info("[ class_like 추천 취소 처리 ]");
//
//        // class_like 테이블에서 삭제
//        classLikeRepository.delete(classLikeRequestDto.toClassLike());
//
//        // class_master의 추천 수도 내려줌
//        ClassMaster master = classMasterRepository.findById(classLikeRequestDto.getMasterId()).orElseThrow(() -> new IllegalArgumentException("Master 정보가 없습니다. id =" + classLikeRequestDto.getMasterId()));
//        master.updateLikeCount(master.getLikeCount()-1);
//
//        // 변경된 class_master의 추천수 반환
//        return ClassMasterLikeCountResponseDto.of(master);
//
//    }

    @Transactional
    public ClassMasterLikeCountResponseDto changeLike(ClassLikeRequestDto classLikeRequestDto) {
        log.info("[ class_like 추천 or 취소 처리 ]");

        // 중복인지 확인
        Boolean checkLike = checkLike(classLikeRequestDto);
        log.info("[ checkLike ]" + checkLike);

        if(!checkLike){
            log.info("- 추천된 적 없는 class ");
            // 중복이 아니면 class_like 테이블에 저장
            classLikeRepository.save(classLikeRequestDto.toClassLike());

            // class_master의 추천 수도 올려줌
            ClassMaster master = classMasterRepository.findById(classLikeRequestDto.getMasterId()).orElseThrow(() -> new IllegalArgumentException("Master 정보가 없습니다. id =" + classLikeRequestDto.getMasterId()));
            master.updateLikeCount(master.getLikeCount()+1);

            // 변경된 class_master 반환
            return ClassMasterLikeCountResponseDto.of(master);
        } else {
            // 중복이면 (이미 추천했으면) 추천 취소 처리
            log.info("- 이미 추천된 class -> 취소 처리 ");

            // class_like 테이블에서 삭제
            classLikeRepository.delete(classLikeRequestDto.toClassLike());

            // class_master의 추천 수도 내려줌
            ClassMaster master = classMasterRepository.findById(classLikeRequestDto.getMasterId()).orElseThrow(() -> new IllegalArgumentException("Master 정보가 없습니다. id =" + classLikeRequestDto.getMasterId()));
            master.updateLikeCount(master.getLikeCount()-1);

            // 변경된 class_master 반환
            return ClassMasterLikeCountResponseDto.of(master);
        }
    }

    /* 이미 추천한 클래스인지 확인 */
    @Transactional(readOnly = true)
    public boolean checkLike(ClassLikeRequestDto classLikeRequestDto) {

        return classLikeRepository.checkLike(classLikeRequestDto.getMasterId(), classLikeRequestDto.getMemberId());

    }

    /* 한 클래스 게시물에 추천한 회원 조회 */
    @Transactional(readOnly = true)
    public List<ClassLikeResponseDto> findLikeByMasterId(Long masterId) {
        log.info("[ 한 class의 추천회원들 조회 ]");

        return classLikeRepository.findByMasterId(masterId).stream()
                .map(ClassLikeResponseDto::new)
                .collect(Collectors.toList());
    }

    /* 한 회원이 추천한 클래스 조회 */
    @Transactional(readOnly = true)
    public List<ClassLikeResponseDto> findLikeByMemberId(Long memberId) {
        log.info("[ 한 회원의 추천 class 조회 ]");

        return classLikeRepository.findByMemberId(memberId).stream()
                .map(ClassLikeResponseDto::new)
                .collect(Collectors.toList());

    }

    /* 전체 추천 테이블 조회 */
    @Transactional(readOnly = true)
    public List<ClassLikeResponseDto> findLikes() {
        log.info("[ 전체 추천 조회 ]");

        return classLikeRepository.findAll().stream()
                .map(ClassLikeResponseDto::new)
                .collect(Collectors.toList());
    }


}
