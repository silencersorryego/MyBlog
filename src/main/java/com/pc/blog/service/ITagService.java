package com.pc.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pc.blog.domain.Tag;
import com.pc.blog.mapper.ITagMapper;

import java.util.List;

public interface ITagService extends IService<Tag> {
    List<Tag> listTagAndCount();
}
