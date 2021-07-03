package com.timeline.controller.dto.timeline.comment;

import com.timeline.entity.TimelineComment;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-06-18 오전 3:26
 * @brief :
 **/
@Getter
@NoArgsConstructor
public class TimelineCommentCheckRequestDto {

    private Long masterId; // timeline_master_id
    private String nickname; // member_nickname


}
