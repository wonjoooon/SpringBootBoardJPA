package com.wonjun.controller;

import com.wonjun.model.entity.Bulletin;
import com.wonjun.service.BulletinService;
import com.wonjun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/sku")
public class BulletinController {
    @Autowired
    private BulletinService bulletinService;
    @Autowired
    private UserService userService;

    @RequestMapping({"/list", "/"})
    public String getArticleList(Model model,
                                 @PageableDefault(sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {
        //List<Bulletin> articleList = bulletinService.listArticle();
        Page<Bulletin> articleList = bulletinService.pageList(pageable);
        model.addAttribute("dataList", articleList);

        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());

        model.addAttribute("hasNext", articleList.hasNext());
        model.addAttribute("hasPrev", articleList.hasPrevious());
        return "list";
    }

    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public String writeArticle() {
        return "write";
    }

    @PostMapping("/addarticle")
    @PreAuthorize("isAuthenticated()")
    public String addArticle(@RequestParam(value = "i_title") String title,
                             @RequestParam(value = "i_content") String content,
                             Principal principal) {
        Bulletin bulletin = new Bulletin();
        bulletin.setTitle(title);
        bulletin.setContent(content);
        bulletin.setWriter(userService.getUser(principal.getName()));
        // DB에 저장하는 서비스 호출
        bulletinService.addArticle(bulletin);
        return "redirect:list";
    }

    @GetMapping("/view")
    public ModelAndView viewArticle(@RequestParam(value = "no") String articleNo) {
        Bulletin bulletin;
        bulletin = bulletinService.viewArticle(Integer.parseInt(articleNo));

        ModelAndView mv = new ModelAndView();
        mv.setViewName("view");
        mv.addObject("article", bulletin);

        return mv;
    }

    @PostMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public String editArticle(@RequestParam(value = "articleNo") String articleNo,
                              @RequestParam(value = "title") String title,
                              @RequestParam(value = "content") String content,
                              RedirectAttributes attr) {
        Bulletin bulletin = new Bulletin();
        bulletin.setId(Integer.parseInt(articleNo));
        bulletin.setTitle(title);
        bulletin.setContent(content);
        bulletinService.editArticle(bulletin);
        attr.addAttribute("no", articleNo);
        return "redirect:view";
    }

    @PostMapping("/remove")
    @PreAuthorize("isAuthenticated()")
    public String removeArticle(@RequestParam(value = "articleNo") String articleNo) {
        bulletinService.removeArticle(Integer.parseInt(articleNo));
        return "redirect:list";
    }

    @GetMapping("/info")
    @ResponseBody
    public Map<String, String> getInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "sku project");
        map.put("version", "1.0");
        map.put("author", "Tom");

        return map;
    }

    @GetMapping("/dogandcat")
    public String upload() throws Exception {
        return "classifier";
    }
}
