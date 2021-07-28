package com.timeline.entity.timeline;

import com.timeline.entity.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-06-16 오후 6:45
 * @brief : 타임라인 상세페이지의 추천 관리
 **/
@Getter
@NoArgsConstructor
@Table(name = "timeline_like")
@Entity
@IdClass(TimelineLikePK.class)
public class TimelineLike extends BaseTimeEntity {

    @Id
    @Column(name = "timeline_master_id")
    private Long masterId; // timeline_master_id

    @Id
    @Column(name = "member_id")
    private Long memberId; // member_id

    @Builder
    public TimelineLike(Long masterId, Long memberId) {
        this.masterId = masterId;
        this.memberId = memberId;
    }

}
