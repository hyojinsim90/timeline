package com.timeline.controller.dto.timeline;

import com.timeline.entity.timeline.TimelinePicture;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-07-16 오후 5:46
 * @brief : 타임라인 이미지 Dto
 **/
@Getter
public class TimelinePictureResponseDto {

    private Long id;
    private Long timelineMasterId; // timeline_master의 id
    private String originalFileName;
    private String storedFilePath;
    private long fileSize;
    private LocalDateTime createdDate;

    public TimelinePictureResponseDto(TimelinePicture entity) {
        this.id = entity.getId();
        this.timelineMasterId = entity.getTimelineMasterId();
        this.originalFileName = entity.getOriginalFileName();
        this.storedFilePath = entity.getStoredFilePath();
        this.fileSize = entity.getFileSize();
        this.createdDate = entity.getCreatedDate();
    }


}
