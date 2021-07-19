package com.timeline.repository;

import com.timeline.entity.classes.ClassDetail;
import com.timeline.entity.timeline.TimelineDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-07-09 오후 11:12
 * @brief : class detail용 repository
 **/
public interface ClassDetailRepository extends JpaRepository<ClassDetail, Long> {

    @Query("SELECT m FROM ClassDetail m WHERE m.masterId = :masterId")
    List<ClassDetail> findByMasterId(Long masterId);
}
