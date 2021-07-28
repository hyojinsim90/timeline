package com.timeline.repository;

import com.timeline.controller.dto.classes.ClassMasterResponseDto;
import com.timeline.entity.classes.ClassMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-07-09 오후 11:01
 * @brief : class master 관련 repository
 **/
public interface ClassMasterRepository extends JpaRepository<ClassMaster, Long> {

    List<ClassMaster> findByOrganizerEmail(String organizerEmail);

    List<ClassMaster> findTop10ByOrderByLikeCountDesc();

    @Query("SELECT m FROM ClassMaster m WHERE m.category = :category")
    List<ClassMaster> findByCategory(@Param("category") String category);

    @Query("SELECT m FROM ClassMaster m WHERE m.category like ':category' and m.priceSorting like ':priceSorting' and m.placeSorting like ':placeSorting' and m.className like ':keyword'")
    List<ClassMaster> searchByKeyword(@Param("category") String category, @Param("priceSorting") String priceSorting, @Param("placeSorting") String placeSorting, @Param("keyword") String keyword);

    @Query("SELECT viewCount+1 FROM ClassMaster m WHERE m.id = :id")
    int updateView(@Param("id") Long id);

    @Query("SELECT likeCount+1 FROM ClassMaster m WHERE m.id = :id")
    int updateLike(@Param("id") Long id);

    @Query("SELECT viewCount FROM ClassMaster m WHERE m.id = :id")
    int viewCount(Long id);

    @Query("SELECT likeCount FROM ClassMaster m WHERE m.id = :id")
    int likeCount(Long id);

    List<ClassMaster> findAllByOrderByIdAsc();

    List<ClassMaster> findAllByOrderByIdDesc();

    List<ClassMaster> findAllByOrderByLikeCountDesc();

    ClassMaster findByMasterId(Long masterId);
}
