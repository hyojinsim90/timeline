package com.timeline.controller.dto.timeline;

import com.timeline.entity.TimelineMaster;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-06-02 오후 2:27
 * @brief : 타임라인 마스터 목록 반환 DTO
 **/
@Getter
public class TimelineMasterListResponseDto {

    private Long id; // pk
    private String title; // 제목
    private String author; // 작성자 - member의 email
    private String filePath; // 대표이미지
    private String category; // 분야
    private int viewCount; // 조회수
    private int likeCount; // 추천수
    private boolean isOpen; // 공개여부
    private boolean isComplete; // 진행여부
    private String imgFullPath; // CloudFront 주소 (이미지를 불러올때는 cloudFront도메인명+key가 되야함)
    private LocalDateTime createdDate;

    public TimelineMasterListResponseDto(TimelineMaster entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.filePath = entity.getFilePath();
        this.category = entity.getCategory();
        this.viewCount = entity.getViewCount();
        this.likeCount = entity.getLikeCount();
        this.isOpen = entity.isOpen();
        this.isComplete = entity.isComplete();
//        this.imgFullPath = "https://" + S3Service.CLOUD_FRONT_DOMAIN_NAME + "/" + entity.getFilePath();
        this.createdDate = entity.getCreatedDate();
    }

}
