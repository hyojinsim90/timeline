package com.timeline.repository;

import com.timeline.entity.TimelineComment;
import com.timeline.entity.TimelineLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-06-16 오후 6:54
 * @brief : timeline_comment 관련 repository
 **/
@Repository
public interface TimelineCommentRepository extends JpaRepository<TimelineComment, Long> {

    @Query("SELECT m FROM TimelineComment m WHERE m.masterId = :masterId AND m.memberId = :nickname")
    TimelineComment findExistOne(@Param("masterId") Long masterId, @Param("memberId") String nickname);

}
