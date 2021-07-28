package com.timeline.entity.classes;

import com.timeline.controller.dto.classes.ClassMasterUpdateRequestDto;
import com.timeline.entity.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-07-09 오후 10:51
 * @brief : class master entity
 **/

@Getter
@NoArgsConstructor
@Table(name = "class_master")
@Entity
public class ClassMaster extends BaseTimeEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id; // pk

    @Column(name = "master_id")
    private Long masterId; // 타임라인 id

    @Column(name = "nickname")
    private String nickname; // 멤버 nickname

    @Column(name="view_count")
    private int viewCount; // 조회수

    @Column(name="like_count")
    private int likeCount; // 추천수

    @Column(name = "recuit_start_date")
    private String recuitStartDate; // 모집시작일

    @Column(name = "recuit_end_date")
    private String recuitEndDate; // 모집종료일

    @Column(name = "priceSorting") // 가격정책 구분
    private String priceSorting;

    @Column(name = "bank")
    private String bank; // 은행

    @Column(name = "account")
    private Long account; // 계좌번호

    @Column(name = "depositor")
    private String depositor; // 예금주성명

    @Column(name = "category")
    private String category; // 분야

    @Column(name = "class_name")
    private String className; // 클래스명

    @Column(name = "simple_info")
    private String simpleInfo; // 간단 소개

    @Column(name = "place_sorting")
    private String placeSorting; // 장소구분

    @Column(name="place")
    private String place; // 장소상세

    @Column(name = "class_start_date")
    private String classStartDate; // 클래스 시작일

    @Column(name = "class_end_date")
    private String classEndDate; // 클래스 종료일

    @Column(name = "detail_info")
    private String detailInfo; // 상세정보

    @Column(name = "organizer_name")
    private String organizerName; // 담당자 성명

    @Column(name = "organizer_email")
    private String organizerEmail; // 담당자 이메일

    @Builder
    public ClassMaster(Long masterId,
                       String nickname,
                       int viewCount,
                       int likeCount,
                       String recuitStartDate,
                       String recuitEndDate,
                       String priceSorting,
                       String bank,
                       Long account,
                       String depositor,
                       String category,
                       String className,
                       String simpleInfo,
                       String placeSorting,
                       String place,
                       String classStartDate,
                       String classEndDate,
                       String detailInfo,
                       String organizerName,
                       String organizerEmail) {
        this.masterId = masterId;
        this.nickname = nickname;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.recuitStartDate = recuitStartDate;
        this.recuitEndDate = recuitEndDate;
        this.priceSorting = priceSorting;
        this.bank = bank;
        this.account = account;
        this.depositor = depositor;
        this.category = category;
        this.className = className;
        this.simpleInfo = simpleInfo;
        this.placeSorting = placeSorting;
        this.place = place;
        this.classStartDate = classStartDate;
        this.classEndDate = classEndDate;
        this.detailInfo = detailInfo;
        this.organizerName = organizerName;
        this.organizerEmail = organizerEmail;
    }


    public void update(ClassMasterUpdateRequestDto masterUpdateDto) {
        this.recuitStartDate = masterUpdateDto.getRecuitStartDate(); // 모집시작일
        this.recuitEndDate = masterUpdateDto.getRecuitEndDate(); // 모집종료일
        this.bank = masterUpdateDto.getBank(); // 은행
        this.account = masterUpdateDto.getAccount(); // 계좌번호
        this.depositor = masterUpdateDto.getDepositor(); // 예금주성명
        this.category = masterUpdateDto.getCategory(); // 분야
        this.className = masterUpdateDto.getClassName(); // 클래스명
        this.simpleInfo = masterUpdateDto.getSimpleInfo(); // 간단 소개
        this.placeSorting = masterUpdateDto.getPlaceSorting(); // 장소구분
        this.classStartDate = masterUpdateDto.getClassStartDate(); // 클래스 시작일
        this.classEndDate = masterUpdateDto.getClassEndDate(); // 클래스 종료일
        this.detailInfo = masterUpdateDto.getDetailInfo(); // 상세정보
        this.organizerName = masterUpdateDto.getOrganizerName(); // 담당자 성명
        this.organizerEmail = masterUpdateDto.getOrganizerEmail(); // 담당자 이메일
    }

    public void updateView(int viewCount){ this.viewCount = viewCount; }

    public void updateLike(int likeCount){ this.likeCount = likeCount; }

}
