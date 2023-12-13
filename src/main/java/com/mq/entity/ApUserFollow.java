package com.mq.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("ap_user_follow")
@Data
public class ApUserFollow implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField("follow_id")
    private Long followId;

    @TableField("follow_name")
    private String followName;

    @TableField("level")
    private Short level;

    @TableField("is_notice")
    private Short isNotice;

    @TableField("created_time")
    private Date createdTime;
}
