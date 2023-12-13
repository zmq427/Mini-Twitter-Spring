package com.mq.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("like_document")
public class ApLikeDocument {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("datetime_create")
    private Date datetimeCreate;

    @TableField("voter_id")
    private Integer voterId;

    @TableField("content_id")
    private Integer contentId;

    @TableField("voting")
    private Short voting;

    @TableField("votes")
    private Integer votes;

    @TableField("create_date")
    private Date createDate;
}
