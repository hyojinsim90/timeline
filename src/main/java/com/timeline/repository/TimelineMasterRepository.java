package com.timeline.repository;

import com.timeline.entity.timeline.TimelineMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
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

    List<TimelineMaster> findByAuthor(String author);

    List<TimelineMaster> findTop10ByOrderByViewCountDesc();

    List<TimelineMaster> findTop10ByOrderByLikeCountDesc();

    @Query("SELECT m FROM TimelineMaster m WHERE m.likeCount > 100")
    List<Long> findBestLikes();

    @Query("SELECT m FROM TimelineMaster m WHERE m.category = :category")
    List<TimelineMaster> findByCategory(@Param("category") String category);

    @Query("SELECT m FROM TimelineMaster m WHERE m.category = :category and m.title like ':keyword'")
    List<TimelineMaster> searchByKeyword(@Param("category") String category, @Param("keyword") String keyword);
}

