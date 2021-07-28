package com.timeline.controller.dto.timeline;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-06-02 오후 4:15
 * @brief : 타임라인 마스터 수정 Dto
 **/
@Getter
@Setter
@NoArgsConstructor
public class TimelineMasterUpdateRequestDto {

    private String title; // 제목
//    private String filePath; // 대표이미지(기존)
    private String category; // 분야
    private boolean isOpen; // 공개여부
    private boolean isComplete; // 진행여부

    @Builder
    public TimelineMasterUpdateRequestDto(String title, String filePath, String category, boolean isOpen, boolean isComplete) {
        this.title = title;
//        this.filePath = filePath;
        this.category = category;
        this.isOpen = isOpen;
        this.isComplete = isComplete;
    }
}
