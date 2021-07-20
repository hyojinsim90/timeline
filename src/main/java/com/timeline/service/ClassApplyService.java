package com.timeline.service;

import com.timeline.controller.dto.classes.apply.ClassApplyRequestDto;
import com.timeline.controller.dto.classes.apply.ClassApplyResponseDto;
import com.timeline.entity.classes.ClassApply;
import com.timeline.repository.ClassApplyRepository;
import com.timeline.repository.ClassMasterRepository;
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
 * @date : 2021-06-16 오후 7:37
 * @brief : 클래스 신청 관리 서비스
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class ClassApplyService {

    private final ClassApplyRepository classApplyRepository;

    /* 클래스 신청할 시 */
    @Transactional
    public ClassApplyResponseDto addApply(ClassApplyRequestDto classApplyRequestDto) throws Exception {
        log.info("[ class_Apply 신청 처리 ]");

        boolean checkApply = checkApply(classApplyRequestDto);

        if(checkApply){
             throw new Exception("이미 신청한 클래스입니다");
        } else {
            return new ClassApplyResponseDto(classApplyRepository.save(classApplyRequestDto.toClassApply()));
        }

    }

    /* 클래스 신청 취소할 시 */
    @Transactional
    public boolean deleteApply(Long id) {
        log.info("[ class_Apply 신청 취소 처리 ]");

       ClassApply classApply = classApplyRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("신청 정보가 없습니다. id = " + id));

        if(classApply != null ){
            log.info("- 신청내역 삭제 처리 ");
            classApplyRepository.deleteById(id);
            return true;
        } else {
            log.info("- 삭제할 신청내역 없음 ");
            return false;
        }


    }

    /* 이미 신청한 클래스인지 확인 */
    @Transactional(readOnly = true)
    public boolean checkApply(ClassApplyRequestDto classApplyRequestDto) {

        return classApplyRepository.checkApply(classApplyRequestDto.getMasterId(), classApplyRequestDto.getMemberId());

    }

    /* 한 클래스 게시물에 신청한 회원 조회 */
    @Transactional(readOnly = true)
    public List<ClassApplyResponseDto> findApplyByMasterId(Long masterId) {
        log.info("[ 한 class의 신청회원들 조회 ]");

        return classApplyRepository.findByMasterId(masterId).stream()
                .map(ClassApplyResponseDto::new)
                .collect(Collectors.toList());
    }

    /* 한 회원이 신청한 클래스 조회 */
    @Transactional(readOnly = true)
    public List<ClassApplyResponseDto> findApplyByMemberId(Long memberId) {
        log.info("[ 한 회원의 신청 class 조회 ]");

        return classApplyRepository.findByMemberId(memberId).stream()
                .map(ClassApplyResponseDto::new)
                .collect(Collectors.toList());

    }

    /* 전체 신청 테이블 조회 */
    @Transactional(readOnly = true)
    public List<ClassApplyResponseDto> findApplys() {
        log.info("[ 전체 신청 조회 ]");

        return classApplyRepository.findAll().stream()
                .map(ClassApplyResponseDto::new)
                .collect(Collectors.toList());
    }


}
