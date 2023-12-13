package com.mq.utils;

public class RedisKeyUtil {
    private static final String SPLIT = ":";
    private static final String PREFIX_ENTITY_LIKE = "like:entity";
    private static final String PREFIX_USER_LIKE = "like:user";
    private static final String PREFIX_FOLLOWER = "follower";
    private static final String PREFIX_FOLLOWEE = "followee";
    private static final String PREFIX_KAPTCHA = "kaptcha";
    private static final String PREFIX_UV = "uv";
    private static final String PREFIX_DAU = "dau";
    private static final String PREFIX_POST = "post";

    // 某个实体的赞
    // like:entity:entityType:entityId -> set(userId)
    public static String getEntityLikeKey(int entityType, long entityId) {
        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }
    public static String getUserLikeKey(long userId) {
        return PREFIX_USER_LIKE + SPLIT + userId;
    }

    //某个用户关注的实体
    // followee:userId:entityType -> zset(entityId, Date())
    public static String getFolloweeKey(long userId) {
        return PREFIX_FOLLOWEE + SPLIT + userId;
    }
    // 某个用户拥有的粉丝
    // follower:entityType:entityId -> zset(userId, Date())
    public static String getFollowerKey(long userId) {
        return PREFIX_FOLLOWER + SPLIT + userId;
    }
    // 登录验证码
    public static String getKaptchaKey(String owner) {
        return PREFIX_KAPTCHA + SPLIT + owner;
    }

    // 单日UV
    public static String getUVKey(String date) {
        return PREFIX_UV + SPLIT + date;
    }

    // 区间UV
    public static String getUVKey(String startDate, String endDate) {
        return PREFIX_UV + SPLIT + startDate + SPLIT + endDate;
    }

    // 单日活跃用户
    public static String getDAUKey(String date) {
        return PREFIX_DAU + SPLIT + date;
    }

    // 区间活跃用户
    public static String getDAUKey(String startDate, String endDate) {
        return PREFIX_DAU + SPLIT + startDate + SPLIT + endDate;
    }
    // 帖子分数
    public static String getPostScoreKey() {
        return PREFIX_POST + SPLIT + "score";
    }
}
