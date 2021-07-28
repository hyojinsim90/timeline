package com.timeline.repository;


import com.timeline.entity.timeline.TimelineLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-06-16 오후 6:55
 * @brief : timeline_like(추천관리) 관련 repository
 **/
@Repository
public interface TimelineLikeRepository extends JpaRepository<TimelineLike, Long> {

    @Query("SELECT m FROM TimelineLike m WHERE m.masterId = :masterId AND m.memberId = :memberId")
    TimelineLike findExistOne(@Param("masterId") Long masterId, @Param("memberId") Long memberId);

    @Query("SELECT count(m)>0 FROM TimelineLike m WHERE m.masterId = :masterId AND m.memberId = :memberId")
    boolean checkLike(@Param("masterId") Long masterId, @Param("memberId") Long memberId);

    List<TimelineLike> findByMasterId(Long masterId);

    List<TimelineLike> findByMemberId(Long memberId);

}
