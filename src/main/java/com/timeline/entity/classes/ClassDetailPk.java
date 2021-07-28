package com.timeline.entity.classes;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-07-09 오후 11:11
 * @brief : class_detail PK용 idClass만들기
 **/
@Data
public class ClassDetailPk implements Serializable {
    private Long masterId;
    private Long id;
}
