package com.timeline.repository;

import com.timeline.entity.classes.ClassMaster;
import com.timeline.entity.timeline.TimelineMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.List;

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

    @Query("SELECT m FROM ClassMaster m WHERE m.category like ':category' m.priceSorting like ':priceSorting' m.placeSorting like ':placeSorting' and m.title like ':keyword'")
    List<ClassMaster> searchByKeyword(@Param("category") String category, @Param("category") String priceSorting, @Param("placeSorting") String placeSorting, @Param("keyword") String keyword);

}
