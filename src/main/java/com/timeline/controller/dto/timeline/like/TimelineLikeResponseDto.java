package com.timeline.controller.dto.timeline.like;

import com.timeline.entity.timeline.TimelineLike;
import lombok.Getter;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-06-16 오후 7:48
 * @brief : 타임라인 추천 요청
 **/
@Getter
public class TimelineLikeResponseDto {

    private Long masterId; // timeline_master_id
    private Long memberId; // member_id

    public TimelineLikeResponseDto(TimelineLike entity) {
        this.masterId = entity.getMasterId();
        this.memberId = entity.getMemberId();
    }
}
