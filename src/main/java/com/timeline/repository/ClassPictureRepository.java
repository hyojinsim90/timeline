package com.timeline.repository;

import com.timeline.entity.classes.ClassPicture;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-07-20 오전 5:06
 * @brief :
 **/
public interface ClassPictureRepository extends JpaRepository<ClassPicture, Long> {

    ClassPicture findByClassMasterId(Long classMasterId);

}