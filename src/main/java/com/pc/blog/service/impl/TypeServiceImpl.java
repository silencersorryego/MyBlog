package com.pc.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pc.blog.domain.Type;
import com.pc.blog.mapper.IBlogMapper;
import com.pc.blog.mapper.ITypeMapper;
import com.pc.blog.service.IBlogService;
import com.pc.blog.service.ITypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TypeServiceImpl extends ServiceImpl<ITypeMapper, Type> implements ITypeService {
    @Resource
    ITypeMapper typeMapper;

    @Override
    public List<Type> listTypeAndCount() {
        List<Type> types = typeMapper.selectList(null);
        List<Type> typeList = new ArrayList<>();
        int count = 0;
        for (Type type : types) {
            count = typeMapper.getTypeCount(type.getId());
            if (count>0){
                type.setCount(count);
                typeList.add(type);
            }
        }
        return typeList;
    }
}
