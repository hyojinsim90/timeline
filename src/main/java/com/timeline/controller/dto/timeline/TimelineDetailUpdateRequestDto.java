package com.timeline.controller.dto.timeline;

import lombok.Getter;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-06-02 오후 5:03
 * @brief : timeline_detail 수정용 Dto
 **/
@Getter
public class TimelineDetailUpdateRequestDto {

    private int id; // timeline_detail_id
    private String scheduleDate; // 계획일자
    private String title; // 간단한 일정소개
    private String content; // 일정내용


}
