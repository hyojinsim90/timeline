package com.timeline.controller.dto.classes.like;

import com.timeline.entity.classes.ClassLike;
import com.timeline.entity.timeline.TimelineLike;
import lombok.Getter;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-06-16 오후 7:48
 * @brief : 클래스 추천 요청
 **/
@Getter
public class ClassLikeResponseDto {

    private Long masterId; // class_master_id
    private Long memberId; // member_id

    public ClassLikeResponseDto(ClassLike entity) {
        this.masterId = entity.getMasterId();
        this.memberId = entity.getMemberId();
    }
}
