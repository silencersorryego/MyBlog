package com.pc.blog.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pc.blog.domain.*;
import com.pc.blog.mapper.ITypeMapper;
import com.pc.blog.mapper.es.IBlogMapper_ES;
import com.pc.blog.service.*;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    IBlogService blogService;
    @Autowired
    ITypeService typeService;
    @Autowired
    ITagService tagService;
    @Autowired
    IMyFileService myFileService;
    @Autowired
    IBlogMapper_ES blogMapper_es;
    @Autowired
    IUserService userService;
    @Autowired
    ICommentService commentService;
    
    @RequestMapping("/")
    public String index(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        PageHelper.startPage(pageNum, 4);
        List<Blog> blogs = blogService.indexListBlog();

        List<Type> types = typeService.listTypeAndCount();
        List<Tag> tags = tagService.listTagAndCount();
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("types", types);
        model.addAttribute("tags", tags);
        recommendBlogs(model);
        //model.addAttribute("recommendBlogs",recommendBlogs);
        return "index";
    }

    public void recommendBlogs(Model model){
        PageHelper.startPage(1, 3);
        List<Blog> recommendBlogs = blogService.list(new QueryWrapper<Blog>().select("id", "title").eq("recommended",true).orderByDesc("update_time"));
        model.addAttribute("recommendBlogs",recommendBlogs);
    }

    @GetMapping("/type/{id}")
    public String type(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum, @PathVariable Integer id, Model model) {
        List<Type> types = typeService.listTypeAndCount();
        //-1表示从首页导航点进来的
        if (id == -1) {
            id = types.get(0).getId();
        }
        model.addAttribute("types", types);
        PageHelper.startPage(pageNum, 4);
        List<Blog> blogs = blogService.indexListBlogByTypeId(id);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("activeTypeId", id);
        recommendBlogs(model);
        return "type";
    }
    @GetMapping("/tag/{id}")
    public String tag(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum, @PathVariable Integer id, Model model){
        List<Tag> tags = tagService.listTagAndCount();
        if (id == -1) {
            id = tags.get(0).getId();
        }
        model.addAttribute("tags", tags);
        PageHelper.startPage(pageNum, 4);
        List<Blog> blogs = blogService.indexBlogByTagId(id);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("blogs",blogs);
        model.addAttribute("activeTagId", id);
        recommendBlogs(model);
        return "tag";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Integer id, Model model) {
        Blog blog = blogService.getBlogById(id);
        List<Comment> comments = commentService.list(new QueryWrapper<Comment>().eq("blog_id",id));
        model.addAttribute("comments", comments);
        model.addAttribute("blog", blog);
        recommendBlogs(model);
        //System.out.println(blog);
        return "blog";
    }

    @RequestMapping("file")
    public String toFile(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        PageHelper.startPage(pageNum,6);
        List<MyFile> myFiles = myFileService.list();
        PageInfo<MyFile> pageInfo = new PageInfo<>(myFiles);
        model.addAttribute("pageInfo",pageInfo);
        recommendBlogs(model);
        return "my-file";
    }

    @RequestMapping("about")
    public String about(Model model){
        recommendBlogs(model);
        return "about";
    }

    @RequestMapping("search")
    public String search(Model model,String query,@RequestParam(defaultValue = "0",value = "pageNum") Integer pageNum){
        recommendBlogs(model);
        model.addAttribute("query",query);
        List<Type> types = typeService.listTypeAndCount();
        List<Tag> tags = tagService.listTagAndCount();
        model.addAttribute("types", types);
        model.addAttribute("tags", tags);
        Page<Blog> search = blogMapper_es.search(QueryBuilders.matchQuery("content",query), PageRequest.of(pageNum, 4));
        List<Blog> blogs = new ArrayList<>();
        for (Blog blog : search) {
            blog.setUser(userService.getOne(new QueryWrapper<User>().select("id","avatar","nick_name").eq("id",blog.getUserId())));
            blog.setType(typeService.getById(blog.getTypeId()));
            blogs.add(blog);
        }
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("pageInfo", pageInfo);
        return "index";
    }
    @PostMapping("comment")
    public String comment(Comment comment){

        comment.setCreateTime(new Date());

        commentService.save(comment);

        System.out.println(comment);
        return "redirect:/blog/" + comment.getBlogId();
    }
}
