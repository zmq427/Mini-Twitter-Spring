package com.mq.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 评论信息
 */
@Data
public class ApComment implements Serializable {

    private Long id;       // 评论ID
    private Long userId;          // 评论作者ID
    private Integer entityType;  //评论目标的类型
    private Long entityId;       // 评论目标的ID
    private Long targetId;   // 回复目标的用户ID

    private String content;       // 评论内容

    private Integer isDelete;     // 是否删除（0：未删除；1：已删除

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;      // 创建时间
}
