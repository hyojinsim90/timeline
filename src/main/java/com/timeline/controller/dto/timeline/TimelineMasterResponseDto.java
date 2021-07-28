package com.timeline.controller.dto.timeline;

import com.timeline.entity.timeline.TimelineMaster;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-05-21 오후 7:33
 * @brief : 타임라인 마스터 생성 시 반환 DTO
 **/
@Getter
@AllArgsConstructor
public class TimelineMasterResponseDto {

    private Long id;

    public static TimelineMasterResponseDto of(TimelineMaster timelineMaster) {
        return new TimelineMasterResponseDto(timelineMaster.getId());
    }

}
