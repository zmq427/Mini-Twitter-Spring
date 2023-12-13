package com.mq.service;

import com.mq.entity.ApMessage;
import com.mq.mapper.dao.ApMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApMessageService {
    @Autowired
    private ApMessageMapper apMessageMapper;

    public List<ApMessage> findConversations(int userId, int offset, int limit) {
        return apMessageMapper.selectConversations(userId, offset, limit);
    }

    public int findConversationCount(int userId) {
        return apMessageMapper.selectConversationsCount(userId);
    }

    public List<ApMessage> findLetters(String conversationId, int offset, int limit) {
        return apMessageMapper.selectLetters(conversationId, offset, limit);
    }

    public int findLetterCount(String conversationId) {
        return apMessageMapper.selectLetterCount(conversationId);
    }

    public int findLetterUnreadCount(int userId, String conversationId) {
        return apMessageMapper.selectLetterUnreadCount(userId, conversationId);
    }

    public int addMessage(ApMessage message) {
        return apMessageMapper.insertMessage(message);
    }

    public ApMessage findLatestNotice(int userId, String topic) {
        return apMessageMapper.selectLatestNotice(userId, topic);
    }
    public int findNoticeCount(int userId, String topic) {
        return apMessageMapper.selectNoticeCount(userId, topic);
    }
    public int findNoticeUnreadCount(int userId, String topic) {
        return apMessageMapper.selectNoticeUnreadCount(userId, topic);
    }
}
