package com.timeline.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-06-02 오후 10:13
 * @brief : timeline_detail PK용 idClass만들기
 **/
@Data
public class TimelineDetailPK implements Serializable {
    private Long masterId;
    private int id;
}
