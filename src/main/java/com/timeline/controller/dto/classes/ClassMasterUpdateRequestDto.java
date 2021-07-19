package com.timeline.controller.dto.classes;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-07-20 오전 6:12
 * @brief :
 **/
@Getter
@Setter
@NoArgsConstructor
public class ClassMasterUpdateRequestDto {

    private String recuitStartDate; // 모집시작일
    private String recuitEndDate; // 모집종료일
    private String bank; // 은행
    private Long account; // 계좌번호
    private String depositor; // 예금주성명
    private String category; // 분야
    private String className; // 클래스명
    private String simpleInfo; // 간단 소개
    private String placeSorting; // 장소구분
    private String classStartDate; // 클래스 시작일
    private String classEndDate; // 클래스 종료일
    private String detailInfo; // 상세정보
    private String organizerName; // 담당자 성명
    private String organizerEmail; // 담당자 이메일

}
