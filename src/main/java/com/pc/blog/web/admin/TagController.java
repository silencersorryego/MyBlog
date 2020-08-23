package com.pc.blog.web.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pc.blog.domain.Tag;
import com.pc.blog.domain.Type;
import com.pc.blog.service.ITagService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("admin")
public class TagController {
    @Autowired
    ITagService tagService;

    @GetMapping("tag")
    public String tag(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        PageHelper.startPage(pageNum,4);
        List<Tag> tags = tagService.list(new QueryWrapper<Tag>().orderByAsc("id"));
        PageInfo<Tag> pageInfo = new PageInfo<>(tags);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/tag";
    }


    @RequestMapping("tag/input/{id}")
    public String toInput(@PathVariable("id") Integer id,Model model){
        Tag tag = tagService.getOne(new QueryWrapper<Tag>().eq("id",id));
        if(tag != null){
            model.addAttribute(tag);
        }
        return "admin/tag-input";
    }

    @RequiresRoles("admin")
    @PostMapping("tag")
    public String saveOrUpdate(Tag tag,RedirectAttributes redirectAttributes){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("name",tag.getName());
        Tag tag1 = tagService.getOne(wrapper);
        if(tag1 != null){
            redirectAttributes.addFlashAttribute("message","不可以添加重复的标签");
            return "redirect:/admin/tag/input/0";
        }
        if(tagService.saveOrUpdate(tag)){
            redirectAttributes.addFlashAttribute("message", "操作成功");
        }else {
            redirectAttributes.addFlashAttribute("message", "操作失败");
        }
        return "redirect:/admin/tag";
    }

    @RequiresRoles("admin")
    @RequestMapping("tag/delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        boolean b = tagService.removeById(id);
        return "redirect:/admin/tag";
    }


}
