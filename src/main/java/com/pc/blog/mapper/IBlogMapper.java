package com.pc.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pc.blog.domain.Blog;
import com.pc.blog.domain.Tag;
import com.pc.blog.domain.Type;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IBlogMapper extends BaseMapper<Blog> {

    List<Blog> listAdminBlog();
    int saveBlogIdAndTagId(@Param("blogId") Integer blogId,@Param("tagId") Integer tagId);
    int deleteBlogIdAndTagId(Integer blogId);
    List<Blog> getBlogBySearch(Blog blog);
    List<Blog> indexListBlog();
    List<Blog> indexListBlogByTypeId(Integer typeId);
    List<Blog> indexBlogByTagId(Integer id);

    @Select("select t.id,t.name from tb_blog_tag bt inner join tb_tag t on bt.tag_id=t.id where blog_id = #{blog_id}")
    List<Tag> listBlogTag(int blog_id);
}
