package com.timeline.repository;

import com.timeline.entity.TimelineDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-05-22 오전 2:45
 * @brief :
 **/
@Repository
public interface TimelineDetailRepository extends JpaRepository<TimelineDetail, Long> {

}
