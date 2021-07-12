package com.timeline.repository;

import com.timeline.entity.timeline.TimelineDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-05-22 오전 2:45
 * @brief :
 **/
@Repository
public interface TimelineDetailRepository extends JpaRepository<TimelineDetail, Long> {

    @Query("SELECT m FROM TimelineDetail m WHERE m.masterId = :masterId AND m.id = :id")
    TimelineDetail findDetail(@Param("masterId") Long masterId, @Param("id") Long id);

    @Query("SELECT m FROM TimelineDetail m WHERE m.masterId = :masterId ORDER BY scheduleDate")
    List<TimelineDetail> findByMasterId(Long masterId);

}
