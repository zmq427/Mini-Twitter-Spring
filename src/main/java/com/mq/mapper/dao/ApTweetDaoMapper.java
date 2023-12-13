package com.mq.mapper.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApTweetDaoMapper {
    void updateTweetCommentCount(Long id, Integer count);
}
