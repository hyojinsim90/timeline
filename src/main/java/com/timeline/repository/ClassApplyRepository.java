package com.timeline.repository;

import com.timeline.entity.classes.ClassApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-07-09 오후 11:30
 * @brief :
 **/
public interface ClassApplyRepository extends JpaRepository<ClassApply, Long> {

    @Query("SELECT count(m)>0 FROM ClassApply m WHERE m.masterId = :masterId AND m.memberId = :memberId")
    boolean checkApply(@Param("masterId") Long masterId, @Param("memberId") Long memberId);

    List<ClassApply> findByMasterId(Long masterId);

    List<ClassApply> findByMemberId(Long memberId);

}
