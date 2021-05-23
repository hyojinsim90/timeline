package com.timeline.controller.dto.timeline;

import com.timeline.controller.dto.member.MemberResponseDto;
import com.timeline.entity.Member;
import com.timeline.entity.TimelineMaster;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-05-21 오후 7:33
 * @brief :
 **/
@Getter
@AllArgsConstructor
public class TimelineMasterResponseDto {

    private Long id;

    public static TimelineMasterResponseDto of(TimelineMaster timelineMaster) {
        return new TimelineMasterResponseDto(timelineMaster.getId());
    }

}
