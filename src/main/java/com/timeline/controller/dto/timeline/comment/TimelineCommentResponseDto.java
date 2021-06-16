package com.timeline.controller.dto.timeline.comment;

import com.timeline.entity.TimelineComment;
import lombok.Getter;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-06-16 오후 7:26
 * @brief : 타임라인 저장 반환 DTO
 **/
@Getter
public class TimelineCommentResponseDto {

    private Long masterId; // timeline_master_id
    private String nickname; // member_nickname
    private Long star; // 별점
    private String content; // 내용

    public TimelineCommentResponseDto(TimelineComment entity) {
        this.masterId = entity.getMasterId();
        this.nickname = entity.getNickname();
        this.star = entity.getStar();
        this.content = entity.getContent();
    }

}
