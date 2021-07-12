package com.timeline.controller.dto.timeline.like;

import com.timeline.entity.timeline.TimelineLike;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-06-16 오후 7:42
 * @brief : 타임라인 추천 요청
 **/
@Getter
@NoArgsConstructor
public class TimelineLikeRequestDto {

    private Long masterId; // timeline_master_id
    private Long memberId; // member_id

    public TimelineLike toTimelineLike() {
        return TimelineLike.builder()
                .masterId(masterId)
                .memberId(memberId)
                .build();
    }


}
