package com.timeline.repository;

import com.timeline.entity.classes.ClassLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-07-09 오후 11:18
 * @brief :
 **/
@Repository
public interface ClassLikeRepository extends JpaRepository<ClassLike, Long> {

    @Query("SELECT m FROM ClassLike m WHERE m.masterId = :masterId AND m.memberId = :memberId")
    ClassLike findExistOne(@Param("masterId") Long masterId, @Param("memberId") Long memberId);

    @Query("SELECT count(m)>0 FROM ClassLike m WHERE m.masterId = :masterId AND m.memberId = :memberId")
    boolean checkLike(@Param("masterId") Long masterId, @Param("memberId") Long memberId);

    List<ClassLike> findByMasterId(Long masterId);

    List<ClassLike> findByMemberId(Long memberId);

}
