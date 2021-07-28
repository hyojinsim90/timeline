package com.timeline.entity.classes;

import com.sun.istack.NotNull;
import com.timeline.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-07-20 오전 4:47
 * @brief :
 **/
@Entity
@Builder
@Getter
@Table(name = "class_picture")
@AllArgsConstructor
@NoArgsConstructor
public class ClassPicture extends BaseTimeEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "class_master_id")
    private Long classMasterId; // class_master의 id

    @Column(name = "original_file_name")
    private String originalFileName;

    @Column(name = "stored_file_path")
    private String storedFilePath;

    @Column(name = "file_size")
    private long fileSize;
}
