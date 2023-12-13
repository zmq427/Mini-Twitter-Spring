package com.mq.entity.dtos;

import lombok.Data;

@Data
public class LikeDto {
    private long voterId;
    private int entityType;
    private long entityId;
    private long entityAuthorId;
    private long postId;
}
