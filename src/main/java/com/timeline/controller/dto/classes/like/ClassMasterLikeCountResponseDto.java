package com.timeline.controller.dto.classes.like;

import com.timeline.entity.classes.ClassMaster;
import com.timeline.entity.timeline.TimelineMaster;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-06-17 오전 12:59
 * @brief :
 **/
@Getter
@AllArgsConstructor
public class ClassMasterLikeCountResponseDto {

    private int likeCount; // 추천수

    public static ClassMasterLikeCountResponseDto of(ClassMaster classMaster) {
        return new ClassMasterLikeCountResponseDto(classMaster.getLikeCount());
    }
}
