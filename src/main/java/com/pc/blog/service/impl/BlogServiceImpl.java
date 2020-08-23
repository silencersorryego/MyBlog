package com.pc.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pc.blog.domain.Blog;
import com.pc.blog.domain.User;
import com.pc.blog.mapper.IBlogMapper;
import com.pc.blog.mapper.ITypeMapper;
import com.pc.blog.mapper.IUserMapper;
import com.pc.blog.mapper.es.IBlogMapper_ES;
import com.pc.blog.service.IBlogService;
import com.pc.blog.utils.MarkDownUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BlogServiceImpl extends ServiceImpl<IBlogMapper, Blog> implements IBlogService {
    @Resource
    IBlogMapper blogMapper;
    @Resource
    ITypeMapper typeMapper;
    @Resource
    IUserMapper userMapper;
    @Autowired
    IBlogMapper_ES blogMapper_es;


    @Override
    public List<Blog> listAdminBlog() {
        return blogMapper.listAdminBlog();
    }

    @Transactional
    public void saveBlog(Blog blog){
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        blog.setView(0).setCreateTime(new Date()).setUpdateTime(new Date()).setUserId(user.getId());
        blogMapper.insert(blog);
        blogMapper_es.save(blog);
        List<Integer> list = convertToList(blog.getTagIds());
        for (Integer integer : list) {
            blogMapper.saveBlogIdAndTagId(blog.getId(),integer);
        }
    }

    @Override
    public void deleteBlog(Integer blogId) {
        blogMapper.deleteById(blogId);
        blogMapper_es.deleteById(blogId);
        blogMapper.deleteBlogIdAndTagId(blogId);
    }

    @Override
    public List<Blog> getBlogBySearch(Blog blog) {
        return blogMapper.getBlogBySearch(blog);
    }

    @Override
    public List<Blog> indexListBlog() {
        return blogMapper.indexListBlog();
    }

    @Override
    public List<Blog> indexListBlogByTypeId(int typeId) {
        return blogMapper.indexListBlogByTypeId(typeId);
    }

    @Override
    public List<Blog> indexBlogByTagId(Integer id) {
        return blogMapper.indexBlogByTagId(id);
    }

    @Transactional
    @Override
    public Blog getBlogById(Integer id) {
        Blog blog = blogMapper.selectById(id);
        blogMapper.updateById(blog.setView(blog.getView()+1));
        blogMapper_es.save(blog);
        blog.setType(typeMapper.selectById(blog.getTypeId()));
        blog.setUser(userMapper.selectById(blog.getUserId()));
        blog.setContent(MarkDownUtils.MarkDownToHtmlExtension(blog.getContent()));
        blog.setTags(blogMapper.listBlogTag(id));
        return blog;
    }

    private List<Integer> convertToList(String ids) {
        List<Integer> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Integer(idarray[i]));
            }
        }
        return list;
    }
}
