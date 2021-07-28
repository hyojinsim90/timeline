package com.timeline.controller.dto.timeline;

import com.timeline.entity.timeline.TimelineMaster;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-06-17 오전 12:59
 * @brief :
 **/
@Getter
@AllArgsConstructor
public class TimelineMasterLikeCountResponseDto {

    private int likeCount; // 추천수

    public static TimelineMasterLikeCountResponseDto of(TimelineMaster timelineMaster) {
        return new TimelineMasterLikeCountResponseDto(timelineMaster.getLikeCount());
    }
}
