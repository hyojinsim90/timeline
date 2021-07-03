package com.timeline.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-06-16 오후 6:36
 * @brief : 타임라인 댓글 entity
 **/
@Getter
@NoArgsConstructor
@Table(name = "timeline_comment")
@Entity
@IdClass(TimelineCommentPK.class)
public class TimelineComment extends BaseTimeEntity {

    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Id
    @Column(name = "timeline_master_id")
    private Long masterId; // timeline_master_id

    @Id
    @Column(name = "member_nickname")
    private String nickname; // member_nickname

    @Column(name = "star")
    private Long star; // 별점

    @Column(name = "content")
    private String content; // 내용

    @Builder
    public TimelineComment(Long masterId, String nickname, Long star, String content) {
        this.masterId = masterId;
        this.nickname = nickname;
        this.star = star;
        this.content = content;
    }

    public void update(String nickname, Long star, String content) {
        this.nickname = nickname;
        this.star = star;
        this.content = content;
    }
}



