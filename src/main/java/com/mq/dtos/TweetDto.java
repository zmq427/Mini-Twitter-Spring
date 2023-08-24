package com.mq.dtos;

import lombok.Data;

@Data
public class TweetDto {
    private Long authorId;

    private String authorName;

    private String channelName;

    private Short layout;

    private Short flag;

    private String images;

    private String labels;
}
