package com.mq.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

@TableName("ap_article")
@Data
public class ApTweet {
    @TableId(value = "id", type = IdType.AUTO)
    private BigInteger id;
    
    @TableField("author_id")
    private Long authorId;
    
    @TableField("author_name")
    private String authorName;

    @TableField("channel_id")
    private Long channelId;

    @TableField("channel_name")
    private String channelName;

    @TableField("layout")
    private Short layout;

    @TableField("flag")
    private Short flag;

    @TableField("images")
    private String images;

    @TableField("labels")
    private String labels;

    @TableField("likes")
    private Long likes;

    @TableField("reposts")
    private Long reposts;

    @TableField("comment")
    private Long comment;

    @TableField("views")
    private Long views;

    @TableField("created_time")
    private Date createdTime;

    @TableField("published_time")
    private Date publishedTime;

    @TableField("sync_status")
    private Short syncStatus;

    @TableField("origin")
    private Short origin;

    @TableField("static_url")
    private String staticUrl;
}
