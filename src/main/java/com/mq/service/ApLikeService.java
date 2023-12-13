package com.mq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mq.common.R;
import com.mq.entity.ApLikeDocument;
import com.mq.entity.dtos.LikeDto;

public interface ApLikeService extends IService<ApLikeDocument> {

    void like(LikeDto dto);
    long findEntityLikeCount(int entityType, long entityId);
    int findEntityLikeStatus(long userId, int entityType, long entityId);
}
