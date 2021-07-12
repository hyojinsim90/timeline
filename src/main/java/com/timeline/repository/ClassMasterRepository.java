package com.timeline.repository;

import com.timeline.entity.classes.ClassMaster;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-07-09 오후 11:01
 * @brief : class master 관련 repository
 **/
public interface ClassMasterRepository extends JpaRepository<ClassMaster, Long> {


}
