package com.timeline.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-05-22 오전 2:32
 * @brief : timeline detail entity
 **/
@Getter
@NoArgsConstructor
@Table(name = "timeline_detail")
@Entity
@IdClass(TimelineDetailPK.class)
public class TimelineDetail  extends BaseTimeEntity {

    @Id
    @Column(name = "master_id")
    private Long masterId; // timeline_master_id

    @Id
    @Column(name = "id")
    private Long id; // timeline_detail_id

    @Column(name = "schedule_date", unique = false)
    private String scheduleDate; // 계획일자

    @Column(name = "title")
    private String title; // 간단한 일정소개

    @Column(name = "content")
    private String content; // 일정내용

    @Builder
    public TimelineDetail(Long masterId, Long id, String scheduleDate, String title, String content) {
        this.masterId = masterId;
        this.id = id;
        this.scheduleDate = scheduleDate;
        this.title = title;
        this.content = content;
    }

    public void update(String scheduleDate, String title, String content) {
        this.scheduleDate = scheduleDate;
        this.title = title;
        this.content = content;
    }

}
