package com.timeline.controller.dto.classes;

import com.sun.istack.NotNull;
import com.timeline.entity.classes.ClassPicture;
import lombok.Getter;

import javax.persistence.Column;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-07-21 오전 1:32
 * @brief :
 **/
@Getter
public class ClassPictureResponseDto {

    private Long id;
    private Long classMasterId; // class_master의 id
    private String originalFileName;
    private String storedFilePath;
    private long fileSize;

    public ClassPictureResponseDto(ClassPicture entity) {
        this.id = entity.getId();
        this.classMasterId = entity.getClassMasterId();
        this.originalFileName = entity.getOriginalFileName();
        this.storedFilePath = entity.getStoredFilePath();
        this.fileSize = entity.getFileSize();
    }
}
