package com.timeline.entity.classes;

import com.timeline.entity.BaseTimeEntity;
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
    private String masterId; // 타임라인 id

    @Column(name="like_count")
    private int likeCount; // 추천수

    @Column(name = "recuit_start_date")
    private String recuitStartDate; // 모집시작일

    @Column(name = "recuit_end_date")
    private String recuitEndDate; // 모집종료일

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

    @Column(columnDefinition = "TEXT")
    private String filePath; // 대표이미지

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


}
