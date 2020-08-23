package com.pc.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pc.blog.domain.Tag;
import org.apache.ibatis.annotations.Select;

public interface ITagMapper extends BaseMapper<Tag> {
    @Select("select count(tag_id) from tb_blog_tag where tag_id = #{tag_id}")
    int getTagCount(int tag_id);  //获取tag列表
}
