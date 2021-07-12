package com.timeline.repository;

import com.timeline.entity.classes.ClassLike;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-07-09 오후 11:18
 * @brief :
 **/
public interface ClassLikeRepository extends JpaRepository<ClassLike, Long> {
}