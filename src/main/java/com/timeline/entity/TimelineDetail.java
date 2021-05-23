package com.timeline.entity;

import lombok.Builder;
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
@NoArgsConstructor
@Table(name = "timeline_detail")
@Entity
public class TimelineDetail  extends BaseTimeEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // pk

    @Column(name = "master_id")
    private Long masterId; // timeline_master_id

    @Column(name = "schedule_date", unique = true)
    private LocalDate scheduleDate; // 계획일자

    @Column(name = "titile")
    private String title; // 간단한 일정소개

    @Column(name = "content")
    private String content; // 일정내용

    @Builder
    public TimelineDetail(Long masterId, LocalDate scheduleDate, String title, String content) {
        this.masterId = masterId;
        this.scheduleDate = scheduleDate;
        this.title = title;
        this.content = content;
    }

}
