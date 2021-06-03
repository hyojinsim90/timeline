package com.timeline.controller.dto.timeline;

import com.timeline.entity.TimelineDetail;
import com.timeline.entity.TimelineMaster;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDate;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-05-22 오후 8:20
 * @brief : timeline detail 저장용 dto
 **/
@Getter
@Setter
@NoArgsConstructor
public class TimelineDetailSaveRequestDto {

    private Long masterId; // timeline_master_id
    private Long id; // timeline_detail_id
    private String scheduleDate; // 계획일자
    private String title; // 간단한 일정소개
    private String content; // 일정내용

    public TimelineDetail toTimelineDetail(LocalDate scheduleDate) {
        return TimelineDetail.builder()
                .masterId(masterId)
                .id(id)
                .scheduleDate(scheduleDate)
                .title(title)
                .content(content)
                .build();
    }

}
