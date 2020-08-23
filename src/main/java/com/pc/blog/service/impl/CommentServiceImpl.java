package com.pc.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pc.blog.domain.Comment;
import com.pc.blog.mapper.ICommentMapper;
import com.pc.blog.service.ICommentService;
import org.springframework.stereotype.Service;


@Service
public class CommentServiceImpl extends ServiceImpl<ICommentMapper, Comment> implements ICommentService {
}
