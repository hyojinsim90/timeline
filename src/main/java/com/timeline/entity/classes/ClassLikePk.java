package com.timeline.entity.classes;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-07-09 오후 11:17
 * @brief : class_like PK용 idClass만들기
 **/
@Data
public class ClassLikePk implements Serializable {

    private Long masterId; // class_master_id
    private Long memberId; // member_id

}
