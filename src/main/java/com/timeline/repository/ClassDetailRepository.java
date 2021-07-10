package com.timeline.repository;

import com.timeline.entity.ClassDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-07-09 오후 11:12
 * @brief : class detail용 repository
 **/
public interface ClassDetailRepository extends JpaRepository<ClassDetail, Long> {

}
