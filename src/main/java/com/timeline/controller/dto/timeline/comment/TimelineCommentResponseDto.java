package com.timeline.controller.dto.timeline.comment;

import com.timeline.entity.TimelineComment;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-06-16 오후 7:26w
 * @brief : 타임라인 저장 반환 DTO
 **/
@Getter
public class TimelineCommentResponseDto {

    private Long masterId; // timeline_master_id
    private String nickname; // member_nickname
    private Long star; // 별점
    private String content; // 내용
    private LocalDateTime createdDate; // 생성시각
    private LocalDateTime modifiedDate; // 수정시각

    public TimelineCommentResponseDto(TimelineComment entity) {
        this.masterId = entity.getMasterId();
        this.nickname = entity.getNickname();
        this.star = entity.getStar();
        this.content = entity.getContent();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
    }

}
