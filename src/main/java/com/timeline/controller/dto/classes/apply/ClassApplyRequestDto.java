package com.timeline.controller.dto.classes.apply;

import com.timeline.entity.classes.ClassApply;
import com.timeline.entity.classes.ClassLike;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-06-16 오후 7:42
 * @brief : 클래스 신청
 **/
@Getter
@NoArgsConstructor
public class ClassApplyRequestDto {

    private Long masterId; // class_master_id
    private Long detailId; // class_detail_id
    private Long memberId; // member_id


    public ClassApply toClassApply() {
        return ClassApply.builder()
                .masterId(masterId)
                .detailId(detailId)
                .memberId(memberId)
                .build();
    }


}
