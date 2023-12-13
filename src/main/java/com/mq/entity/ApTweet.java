package com.mq.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.math.BigInteger;
import java.util.Date;
@Document(indexName = "ap_tweet", shards = 6, replicas = 3)
@TableName("ap_tweet")
@Data
public class ApTweet {
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    private BigInteger id;
    
    @TableField("author_id")
    private Long authorId;
    
    @TableField("author_name")
    private String authorName;

    @TableField("content")
    private String content;

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

    @TableField("publish_time")
    private Date publishTime;

    @TableField("sync_status")
    private Short syncStatus;

    @TableField("origin")
    private Short origin;

    @TableField("static_url")
    private String staticUrl;
}
