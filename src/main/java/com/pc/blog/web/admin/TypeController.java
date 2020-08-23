package com.pc.blog.web.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pc.blog.domain.Type;
import com.pc.blog.service.ITypeService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("admin")
public class TypeController {
    @Autowired
    ITypeService typeService;

    @GetMapping("type")
    public String type(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        PageHelper.startPage(pageNum,4);
        List<Type> list = typeService.list(new QueryWrapper<Type>().orderByAsc("id"));
        PageInfo<Type> pageInfo = new PageInfo<>(list);
        model.addAttribute("pageInfo",pageInfo);
        return "admin/type";
    }


    @RequestMapping("type/input/{id}")
    public String toInput(@PathVariable("id") Integer id, Model model){
        Type type = typeService.getOne(new QueryWrapper<Type>().eq("id", id));
        if(type != null){
            model.addAttribute(type);
        }
        return "admin/type-input";
    }

    @RequiresRoles("admin")
    @PostMapping("type")
    public String saveOrUpdate(Type type, RedirectAttributes redirectAttributes){
        Type type1 = typeService.getOne(new QueryWrapper<Type>().eq("name", type.getName()));
        if(type1 != null){
            redirectAttributes.addFlashAttribute("message","不可以添加重复的分类");
            return "redirect:/admin/type/input/0";
        }
        if(typeService.saveOrUpdate(type)){
            redirectAttributes.addFlashAttribute("message", "操作成功");
        }else {
            redirectAttributes.addFlashAttribute("message", "操作失败");
        }
        return "redirect:/admin/type";
    }

    @RequiresRoles("admin")
    @RequestMapping("type/delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        typeService.removeById(id);
        return "redirect:/admin/type";
    }


}
