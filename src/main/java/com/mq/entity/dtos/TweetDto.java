package com.mq.entity.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class TweetDto {

    private String authorName;

    private String content;

    private String channelName;

    private String images;

    // 真正要发布的时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date publishTime;
}
