package com.timeline.controller;

import com.timeline.controller.dto.classes.apply.ClassApplyRequestDto;
import com.timeline.controller.dto.classes.apply.ClassApplyResponseDto;
import com.timeline.service.ClassApplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-06-16 오후 7:35
 * @brief : 클래스 신청 관리 컨트롤러
 **/
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/class")
@RequiredArgsConstructor
public class ClassApplyController {

    private final ClassApplyService classApplyService;

    /* 클래스 신청할 시 */
    @PostMapping("/apply")
    public ClassApplyResponseDto addApply(@RequestBody ClassApplyRequestDto classApplyRequestDto) throws Exception {
        return classApplyService.addApply(classApplyRequestDto);
    }

    /* 클래스 신청 취소할 시 */
    @DeleteMapping("/apply")
    public boolean deleteApply(@RequestParam(value = "id") Long id){
        return classApplyService.deleteApply(id);
    }

    /* 이미 신청한 클래스인지 확인 */
    @GetMapping("/apply/check")
    public boolean checkApply(@RequestBody ClassApplyRequestDto classApplyRequestDto){
        return classApplyService.checkApply(classApplyRequestDto);
    }


    /* 한 클래스 게시물에 신청한 회원 조회 */
    @GetMapping("/apply/list/masterId/{masterId}")
    public List<ClassApplyResponseDto> findApplyByMasterId(@PathVariable Long masterId){
        return classApplyService.findApplyByMasterId(masterId);
    }

    /* 한 회원이 신청한 클래스 조회 */
    @GetMapping("/apply/list/memberId/{memberId}")
    public List<ClassApplyResponseDto> findApplyByMemberId(@PathVariable Long memberId){
        return classApplyService.findApplyByMemberId(memberId);
    }

    /* 전체 신청 테이블 조회 */
    @GetMapping("/apply/list")
    public List<ClassApplyResponseDto> findApplys(){
        return classApplyService.findApplys();
    }


}
