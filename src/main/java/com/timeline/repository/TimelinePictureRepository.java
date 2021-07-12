package com.timeline.repository;

import com.timeline.entity.timeline.TimelinePicture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-07-12 오전 10:10
 * @brief : 사진을 읽을 때 필요한 repository
 **/
public interface TimelinePictureRepository extends JpaRepository<TimelinePicture, Long> {
    //TimelinePicture save(TimelinePicture timelinePicture);

    TimelinePicture findAllByTimelineIdx(Long timelineIdx);

}
