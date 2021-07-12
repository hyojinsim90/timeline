package com.timeline.controller.dto.timeline.comment;

import com.timeline.entity.timeline.TimelineComment;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-06-16 오후 7:18
 * @brief : 타임라인 댓글 저장용 dto
 **/
@Getter
@NoArgsConstructor
public class TimelineCommentRequestDto {

    private Long masterId; // timeline_master_id
    private String nickname; // member_nickname
    private Long star; // 별점
    private String content; // 내용

    public TimelineComment toTimelineComment() {
        return TimelineComment.builder()
                .masterId(masterId)
                .nickname(nickname)
                .star(star)
                .content(content)
                .build();
    }

}
