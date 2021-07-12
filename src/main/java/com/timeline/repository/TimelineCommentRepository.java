package com.timeline.repository;

import com.timeline.entity.timeline.TimelineComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-06-16 오후 6:54
 * @brief : timeline_comment 관련 repository
 **/
@Repository
public interface TimelineCommentRepository extends JpaRepository<TimelineComment, Long> {

    @Query("SELECT m FROM TimelineComment m WHERE m.masterId = :masterId AND m.nickname = :nickname")
    TimelineComment findExistOne(@Param("masterId") Long masterId, @Param("nickname") String nickname);

    List<TimelineComment> findByMasterIdOrderByCreatedDateAsc(Long masterId);

    List<TimelineComment> findByNicknameOrderByCreatedDateAsc(String nickname);

    List<TimelineComment>  findAllByOrderByCreatedDateAsc();
}
