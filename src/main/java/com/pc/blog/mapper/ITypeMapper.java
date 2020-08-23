package com.pc.blog.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pc.blog.domain.Type;
import org.apache.ibatis.annotations.Select;

public interface ITypeMapper extends BaseMapper<Type> {
    @Select("select count(type_id) from tb_blog where type_id = #{type_id}")
    int getTypeCount(int type_id);  //获取tag列表

}
