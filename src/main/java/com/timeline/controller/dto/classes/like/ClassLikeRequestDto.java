package com.timeline.controller.dto.classes.like;

import com.timeline.entity.classes.ClassLike;
import com.timeline.entity.timeline.TimelineLike;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-06-16 오후 7:42
 * @brief : 클래스 추천 요청
 **/
@Getter
@NoArgsConstructor
public class ClassLikeRequestDto {

    private Long masterId; // class_master_id
    private Long memberId; // member_id

    public ClassLike toClassLike() {
        return ClassLike.builder()
                .masterId(masterId)
                .memberId(memberId)
                .build();
    }


}
