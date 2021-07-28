package com.timeline.controller.dto.classes;

import com.timeline.entity.classes.ClassDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-07-20 오전 5:29
 * @brief :
 **/
@Getter
@NoArgsConstructor
public class ClassDetailSaveRequestDto {

    private Long masterId; // 클래스 master_id
    private Long id; // 클래스 detail_id
    private String groupName; // 그룹명
    private int quantity; // 수량
    private int price; // 금액

    public ClassDetail toClassDetail() {
        return ClassDetail.builder()
                .masterId(masterId)
                .id(id)
                .groupName(groupName)
                .quantity(quantity)
                .price(price)
                .build();
    }

}
