package com.timeline.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-05-21 오후 4:58
 * @brief : timeline master entity
 **/

@Getter
@NoArgsConstructor
@Table(name = "timeline_master")
@Entity
public class TimelineMaster extends BaseTimeEntity{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // pk

    @Column(name = "title")
    private String title; // 제목

    @Column(name = "author")
    private String author; // 작성자 - member의 email

    @Column(name="category")
    private String category; // 분야

    @Column(name="view_count")
    private int viewCount; // 조회수

    @Column(name="like_count")
    private int likeCount; // 추천수

    @Column(name="req_count")
    private int reqCount; // 클래스 요청수

    @Column(name="is_open")
    private boolean isOpen; // 공개여부

    @Column(name="is_complete")
    private boolean isComplete; // 진행여부

    @Builder
    public TimelineMaster(String title, String author, String category, int viewCount, int likeCount, int reqCount, boolean isOpen, boolean isComplete) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.reqCount = reqCount;
        this.isOpen = isOpen;
        this.isComplete = isComplete;
    }

}
