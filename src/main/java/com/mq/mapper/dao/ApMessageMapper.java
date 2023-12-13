package com.mq.mapper.dao;

import com.mq.entity.ApMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApMessageMapper {
    // 查询当前用户的会话列表，针对每一个会话只返回一条最新的私信
    List<ApMessage> selectConversations(int userId, int offset, int limit);

    // 查询当前用户的会话数量
    int selectConversationsCount(int userId);

    // 查询某个会话所包含的私信列表
    List<ApMessage> selectLetters(String conversationId, int offset, int limit);

    // 查询某个会话所包含的私信数量
    int selectLetterCount(String conversationId);

    // 查询未读私信数量
    int selectLetterUnreadCount(int userId, String conversationId);

    // 查询某个主题下最新的通知
    ApMessage selectLatestNotice(int userId, String topic);
    // 查询某个主题下通知的数量
    int selectNoticeCount(int userId, String topic);
    // 查询未读的通知的数量
    int selectNoticeUnreadCount(int userId, String topic);
    int insertMessage(ApMessage apMessage);

}
