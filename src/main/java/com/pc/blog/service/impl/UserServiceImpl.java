package com.pc.blog.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pc.blog.domain.User;
import com.pc.blog.mapper.IUserMapper;
import com.pc.blog.service.IUserService;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<IUserMapper, User> implements IUserService {

}
