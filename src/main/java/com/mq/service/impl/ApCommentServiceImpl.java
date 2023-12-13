package com.mq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mq.common.R;
import com.mq.common.constants.ApCommentConstants;
import com.mq.entity.ApComment;
import com.mq.entity.ApTweet;
import com.mq.entity.ApUser;
import com.mq.mapper.ApCommentMapper;
import com.mq.mapper.dao.ApTweetDaoMapper;
import com.mq.service.ApCommentService;
import com.mq.service.ApTweetService;
import com.mq.service.ApUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApCommentServiceImpl extends ServiceImpl<ApCommentMapper, ApComment> implements ApCommentService {
    @Autowired
    ApUserService apUserService;
    @Autowired
    ApTweetDaoMapper apTweetDaoMapper;

    /**
     * 根据推文ID查询对应的评论
     * @param entityId
     * @return
     */
    @Override
    public R selectCommentsByEntity(Integer entityType, Long entityId) {

        LambdaQueryWrapper<ApComment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ApComment::getIsDelete, ApCommentConstants.UNDELETED)
                .eq(ApComment::getEntityType, entityType)
                .eq(ApComment::getEntityId, entityId)
                .orderByAsc(ApComment::getCreateTime);

        // 评论列表
        List<ApComment> comments = list(lambdaQueryWrapper);
        // 评论VO列表
        List<Map<String, Object>> commentVoList = new ArrayList<>();
        if (commentVoList != null) {
            for (ApComment comment: comments) {
                // 评论View Object
                Map<String, Object> commentVo = new HashMap<>();
                commentVo.put("comment", comment);
                commentVo.put("user", apUserService.selectUserById(comment.getUserId()));
                // 回复列表
                List<ApComment> replyList = list(new LambdaQueryWrapper<ApComment>()
                        .eq(ApComment::getIsDelete, ApCommentConstants.UNDELETED)
                        .eq(ApComment::getEntityType, ApCommentConstants.COMMENT)
                        .eq(ApComment::getEntityId, comment.getId()));
                // 回复的VO列表
                List<Map<String, Object>> replyVoList = new ArrayList<>();
                if (replyVoList != null) {
                    for (ApComment reply: replyList) {
                        Map<String, Object> replyVo = new HashMap<>();
                        replyVo.put("reply", reply);
                        replyVo.put("user", apUserService.selectUserById(reply.getUserId()));
                        // 回复目标
                        ApUser targetUser = reply.getTargetId() == 0? null: apUserService.selectUserById(reply.getTargetId());
                        replyVo.put("target", targetUser);

                        replyVoList.add(replyVo);
                    }
                }
                commentVo.put("replys", replyVoList);
                // 回复数量
                int replyCount = selectCountByEntity(ApCommentConstants.COMMENT, comment.getId());
                commentVo.put("replyCount", replyCount);
                commentVoList.add(commentVo);
            }
        }

        return R.success(commentVoList);
    }

    /**
     * 根据推文ID、分页信息查询对应的评论
     * @param entityId
     * @return
     */
    @Override
    public R selectCommentsByEntity(Integer entityType, Long entityId, Integer currentPage, Integer pages) {
        Page<ApComment> rowPage = new Page<>(currentPage, pages);
        LambdaQueryWrapper<ApComment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ApComment::getIsDelete, 0)
                .eq(ApComment::getEntityType, ApCommentConstants.COMMENT)
                .eq(ApComment::getEntityId, entityId)
                .orderByAsc(ApComment::getCreateTime);

        // to be developed...
        return null;
    }

    /**
     * 根据推文ID查询评论数
     * @param entityId
     * @return
     */
    @Override
    public int selectCountByEntity(Integer entityType, Long entityId) {

        LambdaQueryWrapper<ApComment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ApComment::getIsDelete, ApCommentConstants.UNDELETED)
                .eq(ApComment::getEntityType, entityType)
                .eq(ApComment::getEntityId, entityId);
        int count = count(lambdaQueryWrapper);

        return count;
    }

    /**
     * 发布一条评论
     * @param comment
     * @return
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    @Override
    public void publishComment(ApComment comment) {
        if (comment == null) {
            throw new IllegalArgumentException("参数不正确");
        }
        // 把评论写入数据库
        save(comment);
        // 同时更新Tweet表中的评论数
        if (comment.getEntityType() == ApCommentConstants.TWEET) {
            int commentCnt = selectCountByEntity(comment.getEntityType(), comment.getEntityId());
            apTweetDaoMapper.updateTweetCommentCount(comment.getEntityId(), commentCnt+1);
        }
    }



}
