package com.mq.entity.dtos;

import lombok.Data;

import java.util.List;

@Data
public class CommentPullDto {
    private Long tweetId;
    private Integer pageNum;
    private Integer recordPerPage;
}
