package com.pc.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pc.blog.domain.Blog;
import com.pc.blog.domain.MyFile;
import com.pc.blog.mapper.IBlogMapper;
import com.pc.blog.mapper.IMyFileMapper;
import com.pc.blog.service.IBlogService;
import com.pc.blog.service.IMyFileService;
import org.springframework.stereotype.Service;

@Service
public class IMyFileServiceImpl extends ServiceImpl<IMyFileMapper,MyFile> implements IMyFileService{
}
