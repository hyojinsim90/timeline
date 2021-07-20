package com.timeline.controller.dto.classes.apply;

import com.timeline.entity.classes.ClassApply;
import com.timeline.entity.classes.ClassLike;
import lombok.Getter;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-06-16 오후 7:48
 * @brief : 클래스 신청 요청
 **/
@Getter
public class ClassApplyResponseDto {

    private Long id;
    private Long masterId; // class_master_id
    private Long detailId; // class_detail_id
    private Long memberId; // member_id

    public ClassApplyResponseDto(ClassApply entity) {
        this.id = entity.getId();
        this.masterId = entity.getMasterId();
        this.detailId = entity.getDetailId();
        this.memberId = entity.getMemberId();
    }
}
