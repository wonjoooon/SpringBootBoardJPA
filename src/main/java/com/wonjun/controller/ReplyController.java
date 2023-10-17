package com.wonjun.controller;

import com.wonjun.model.entity.Reply;
import com.wonjun.service.BulletinService;
import com.wonjun.service.ReplyService;
import com.wonjun.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/reply")
@Slf4j
public class ReplyController {
    private final ReplyService replyService;
    private final BulletinService bulletinService;
    private final UserService userService;

    @Autowired
    public ReplyController(ReplyService replyService, BulletinService bulletinService, UserService userService) {
        this.replyService = replyService;
        this.bulletinService = bulletinService;
        this.userService = userService;
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public String addReply(@RequestParam(value = "reply_content") String content,
                           @RequestParam(value = "articleNo") String articleNo,
                           @RequestParam(value = "writer") String writer,
                           RedirectAttributes attr, Principal principal) {
        if(principal.getName().equals(writer)){
            log.info("[Add reply] : {}-{}", articleNo,writer);
            Reply reply = new Reply();
            reply.setContent(content);
            reply.setBulletin(bulletinService.viewArticle(Integer.parseInt(articleNo)));
            reply.setWriter(userService.getUser(writer));
            replyService.addReply(reply);
        }


        attr.addAttribute("no", articleNo);
        return "redirect:/sku/view";
    }

    @PostMapping("/remove")
    @PreAuthorize("isAuthenticated()")
    public String removeReply(@RequestParam(value = "id")String id,
                              @RequestParam(value = "articleNo")String articleNo,
                              @RequestParam(value = "writer")String writer,
                              RedirectAttributes attr, Principal principal) {
        if(writer.equals(principal.getName())){
            log.info("[Delete reply] : {}", articleNo);
            replyService.removeReply(Integer.parseInt(id));
        }
        attr.addAttribute("no", articleNo);
        return "redirect:/sku/view";
    }

    @PostMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public String updateReply(@RequestParam(value = "id")String id,
                              @RequestParam(value = "content")String content,
                              @RequestParam(value = "articleNo")String articleNo,
                              @RequestParam(value = "writer")String writer,
                              RedirectAttributes attr, Principal principal) {
        if(writer.equals(principal.getName())){
            log.info("[Update reply] : {}-{}", articleNo,writer);
            replyService.editReply(content, Integer.parseInt(id));
        }
        attr.addAttribute("no", articleNo);
        return "redirect:/sku/view";
    }
}
