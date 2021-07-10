package com.timeline.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-07-09 오후 11:18
 * @brief : 클래스 신청자 관리 클래스
 **/
@Getter
@NoArgsConstructor
@Table(name = "class_apply")
@Entity
public class ClassApply extends BaseTimeEntity{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "class_master_id")
    private Long masterId; // class_master_id

    @Column(name = "class_detail_id")
    private Long detailId; // class_detail_id

    @Column(name = "member_id")
    private Long memberId; // member_id


}
