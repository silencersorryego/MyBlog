package com.pc.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pc.blog.domain.Blog;
import com.pc.blog.domain.Type;

import java.util.List;

public interface IBlogService extends IService<Blog> {

    List<Blog> listAdminBlog();
    void saveBlog(Blog blog);
    void deleteBlog(Integer blogId);
    List<Blog> getBlogBySearch(Blog blog);
    List<Blog> indexListBlog();
    List<Blog> indexListBlogByTypeId(int typeId);
    List<Blog> indexBlogByTagId(Integer id);
    Blog getBlogById(Integer id);
}
