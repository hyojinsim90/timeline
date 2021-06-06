package com.timeline.controller.dto.timeline;

import com.timeline.entity.TimelineMaster;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author : Hyojin Sim
 * @version : 1.0.0
 * @date : 2021-05-21 오후 4:36
 * @brief : timeline master 저장용 dto
 **/
@Getter
@Setter
@NoArgsConstructor
public class TimelineMasterSaveRequestDto {

    private String title;
    private String author;
    private String filePath;
    private String category;
    private boolean isOpen;
    private boolean isComplete;

    public TimelineMaster toTimelineMaster() {
        return TimelineMaster.builder()
                .title(title)
                .author(author)
                .filePath(filePath)
                .category(category)
                .viewCount(0)
                .likeCount(0)
                .isOpen(isOpen)
                .isComplete(isComplete)
                .build();
    }

}
