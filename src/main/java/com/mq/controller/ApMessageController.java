package com.mq.controller;

import com.alibaba.fastjson.JSONObject;
import com.mq.common.R;
import com.mq.entity.ApMessage;
import com.mq.entity.ApUser;
import com.mq.entity.Page;
import com.mq.service.ApMessageService;
import com.mq.service.ApUserService;
import com.mq.utils.LoginThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mq.common.constants.AnnounceConstants.TOPIC_COMMENT;

@RestController
@RequestMapping("/api/message")
public class ApMessageController {
    @Autowired
    private ApMessageService apMessageService;
    @Autowired
    private ApUserService apUserService;
    // 私信列表
    @GetMapping("/letter")
    public String getLetterList(Model model, Page page) {
        ApUser user = LoginThreadLocal.get();

        // 分页信息
        page.setLimit(5);
        page.setPath("/letter/list");
        page.setRows(apMessageService.findConversationCount(user.getId()));
        // 会话列表
        List<ApMessage> conversationList = apMessageService.findConversations(
                user.getId(), page.getOffset(), page.getLimit()
        );
        List<Map<String, Object>> conversations = new ArrayList<>();
        if (conversationList != null) {
            for (ApMessage message: conversationList) {
                Map<String, Object> map = new HashMap();
                map.put("conversation", message);
                map.put("letterCount", apMessageService.findLetterCount(message.getConversationId()));
                map.put("unreadCount", apMessageService.findLetterUnreadCount(user.getId(), message.getConversationId()));
                long targetId = user.getId() == message.getFromId()? message.getToId(): message.getFromId();
                map.put("target", apUserService.selectUserById(targetId));
                conversations.add(map);
            }
        }

        model.addAttribute("conversations", conversations);
        // 查询未读消息数
        int letterUnreadCount = apMessageService.findLetterUnreadCount(user.getId(), "to_be_replaced");
        model.addAttribute("letterUnreadCount", letterUnreadCount);
        
        return "/site/letter";
    }

    @GetMapping("/notice/list")
    public String getNoticeList(Model model) {
        ApUser user = LoginThreadLocal.get();
        // 查询评论类通知
        ApMessage message = apMessageService.findLatestNotice(user.getId(), TOPIC_COMMENT);
        Map<String, Object> messageVO = new HashMap<>();
        if (message != null) {
            messageVO.put("message", message);
            String content = HtmlUtils.htmlEscape(message.getContent());
            HashMap<String, Object> data = JSONObject.parseObject(content, HashMap.class);

            messageVO.put("user", apUserService.selectUserById((Long)data.get("userId")));
            messageVO.put("entityType", data.get("entityType"));
            messageVO.put("entityId", data.get("entityId"));
            messageVO.put("postId", data.get("postId"));

            int count = apMessageService.findNoticeCount(user.getId(), TOPIC_COMMENT);
            messageVO.put("count", count);
            int unread = apMessageService.findNoticeUnreadCount(user.getId(), TOPIC_COMMENT);
            messageVO.put("unread", unread);
        }
        model.addAttribute("commentNotice", messageVO);
        // 查询点赞类通知

        // 查询关注类通知

        return "";
    }
}
