package com.timeline.entity.timeline;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-07-12 오전 10:08
 * @brief : 대표사진에 대한 정보를 저장할 클래스
 **/
@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TimelinePicture {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "timeline_master_id")
    private Long timelineMasterId; // timeline_master의 id

    // 원본 파일이름 과 서버에 저장된 파일 경로 를 분리한 이유?
    // 동일한 이름을 가진 파일이 업로드가 된다면 오류가 생긴다.
    // 이를 해결하기 위함
    @Column(name = "original_file_name")
    private String originalFileName;

    @Column(name = "stored_file_path")
    private String storedFilePath;

    @Column(name = "file_size")
    private long fileSize;
}
