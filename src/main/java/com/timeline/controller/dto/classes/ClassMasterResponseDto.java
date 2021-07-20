package com.timeline.controller.dto.classes;

import com.timeline.entity.classes.ClassMaster;
import com.timeline.entity.timeline.TimelineMaster;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-07-20 오전 5:11
 * @brief :
 **/
@Getter
public class ClassMasterResponseDto {

    private Long id; // pk
    private Long masterId; // 타임라인 id
    private int likeCount; // 추천수
    private String recuitStartDate; // 모집시작일
    private String recuitEndDate; // 모집종료일
    private String priceSorting; // 가격정책 구분
    private String bank; // 은행
    private Long account; // 계좌번호
    private String depositor; // 예금주성명
    private String category; // 분야
    private String className; // 클래스명
    private String simpleInfo; // 간단 소개
    private String placeSorting; // 장소구분
    private String place; // 장소
    private String classStartDate; // 클래스 시작일
    private String classEndDate; // 클래스 종료일
    private String detailInfo; // 상세정보
    private String organizerName; // 담당자 성명
    private String organizerEmail; // 담당자 이메일
    private LocalDateTime createdDate;

    public ClassMasterResponseDto(ClassMaster entity) {
        this.id = entity.getId();
        this.masterId = entity.getMasterId();
        this.likeCount = entity.getLikeCount();
        this.recuitStartDate = entity.getRecuitStartDate();
        this.recuitEndDate = entity.getRecuitEndDate();
        this.priceSorting = entity.getPriceSorting();
        this.bank = entity.getBank();
        this.account = entity.getAccount();
        this.depositor = entity.getDepositor();
        this.category = entity.getCategory();
        this.className = entity.getClassName();
        this.simpleInfo = entity.getSimpleInfo();
        this.placeSorting = entity.getPlaceSorting();
        this.place = entity.getPlace();
        this.classStartDate = entity.getClassStartDate();
        this.classEndDate = entity.getClassEndDate();
        this.detailInfo = entity.getDetailInfo();
        this.organizerName = entity.getOrganizerName();
        this.organizerEmail = entity.getOrganizerEmail();
        this.createdDate = entity.getCreatedDate();
    }

}
