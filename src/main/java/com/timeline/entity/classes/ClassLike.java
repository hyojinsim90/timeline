package com.timeline.entity.classes;

import com.timeline.entity.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-07-09 오후 11:14
 * @brief : class detail 좋아요 관리 class
 **/
@Getter
@NoArgsConstructor
@Table(name = "class_like")
@Entity
@IdClass(ClassLikePk.class)
public class ClassLike extends BaseTimeEntity {

    @Id
    @Column(name = "class_master_id")
    private Long masterId; // class_master_id

    @Id
    @Column(name = "member_id")
    private Long memberId; // member_id
}
