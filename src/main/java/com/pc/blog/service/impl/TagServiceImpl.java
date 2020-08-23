package com.pc.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pc.blog.domain.Tag;
import com.pc.blog.mapper.ITagMapper;
import com.pc.blog.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl extends ServiceImpl<ITagMapper,Tag> implements ITagService {
    @Resource
    ITagMapper tagMapper;

    @Override
    public List<Tag> listTagAndCount() {
        List<Tag> tags = tagMapper.selectList(null);
        List<Tag> tagList = new ArrayList<>();
        int count = 0;
        for (Tag tag : tags) {
            count=tagMapper.getTagCount(tag.getId());
            if (count>0){
                tag.setCount(count);
                tagList.add(tag);
            }
        }
        return tagList;
    }
}
