package com.timeline.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-06-16 오후 6:45
 * @brief : timeline_like PK용 idClass만들기
 **/
@Data
public class TimelineLikePK implements Serializable {

    private Long masterId; // timeline_master_id
    private Long memberId; // member_id

}
