package com.mq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mq.common.R;
import com.mq.entity.ApComment;
import org.springframework.stereotype.Service;

@Service
public interface ApCommentService extends IService<ApComment> {

    R selectCommentsByEntity(Integer entityType, Long entityId);

    R selectCommentsByEntity(Integer entityType, Long entityId, Integer currentPage, Integer pages);

    int selectCountByEntity(Integer entityType, Long entityId);

    void publishComment(ApComment comment);
}
