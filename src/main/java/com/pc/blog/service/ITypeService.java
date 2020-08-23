package com.pc.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pc.blog.domain.Type;

import java.util.List;

public interface ITypeService extends IService<Type> {

    List<Type> listTypeAndCount();
}
