package com.timeline.entity.classes;

import com.timeline.entity.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-07-09 오후 11:03
 * @brief : class detail
 **/

@Getter
@NoArgsConstructor
@Table(name = "class_detail")
@Entity
@IdClass(ClassDetailPk.class)
public class ClassDetail extends BaseTimeEntity {

    @Id
    @Column(name = "master_id")
    private Long masterId; // 클래스 master_id

    @Id
    @Column(name = "id")
    private Long id; // 클래스 detail_id

    @Column(name = "group_name")
    private String groupName; // 그룹명

    @Column(name = "quantity")
    private int quantity; // 수량

    @Column(name = "price")
    private int price; // 금액

    @Builder
    public ClassDetail(Long masterId, Long id, String groupName, int quantity, int price) {
        this.masterId = masterId;
        this.id = id;
        this.groupName = groupName;
        this.quantity = quantity;
        this.price = price;
    }


    public void update(String groupName, int quantity, int price) {
        this.groupName = groupName;
        this.quantity = quantity;
        this.price = price;
    }
}
