package com.mq.mapper.dao;

import com.mq.MiniTwitterApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = MiniTwitterApplication.class)
public class ApTweetDaoMapperTest {
    @Autowired
    ApTweetDaoMapper apTweetDaoMapper;

    @Test
    public void updateTweetCommentCountTest() {
        apTweetDaoMapper.updateTweetCommentCount(1383828014629179394L, 1);
    }
}
