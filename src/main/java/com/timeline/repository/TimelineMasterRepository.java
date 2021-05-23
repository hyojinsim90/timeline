package com.timeline.repository;

import com.timeline.entity.TimelineMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-05-22 오전 2:30
 * @brief :
 **/
public interface TimelineMasterRepository extends JpaRepository<TimelineMaster, Long> {
    @Override
    Optional<TimelineMaster> findById(Long id);

}
