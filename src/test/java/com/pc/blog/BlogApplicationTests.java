package com.pc.blog;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pc.blog.domain.Blog;
import com.pc.blog.domain.Type;
import com.pc.blog.domain.User;
import com.pc.blog.mapper.es.IBlogMapper_ES;
import com.pc.blog.mapper.IBlogMapper;
import com.pc.blog.mapper.IUserMapper;


import com.pc.blog.service.ITagService;
import com.pc.blog.service.ITypeService;
import com.pc.blog.service.IUserService;
import com.pc.blog.utils.ApplicationContextUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
class BlogApplicationTests {
    @Resource
    IUserMapper userMapper;
    @Autowired
    ITagService tagService;
    @Resource
    IBlogMapper blogMapper;
    @Resource
    ITypeService typeService;
    @Autowired
    IBlogMapper_ES blogMapper_es;



    @Test
    void contextLoads() {
    }

    @Test
    void testUser(){
        /*User user = new User(null,"ego","admin4","admin4","897717059@qq.com",true,null,new Date(),new Date());
        userMapper.insert(user);*/

        /*PageHelper.startPage(1, 2);
        List<User> users = userMapper.selectList(null);
        PageInfo pageInfo = new PageInfo<>(users);
        System.out.println(pageInfo);*/

        /*QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.select("user_name","password")
                .eq("user_name","admin");

        User user = userMapper.selectOne(wrapper);
        System.out.println(user);*/
        //blogMapper.selectCount();
    }

    @Test
    void testMd5Hash(){
        //Md5Hash shuaicong = new Md5Hash("040924sc","1234",1024);
        //System.out.println(shuaicong);
        System.out.println(System.getProperty("user.dir"));
        //System.out.println(ApplicationContextUtils.getBean("tagController"));
    }

    @Test
    void testTagService(){
       /* Tag tag = new Tag();
        tag.setName("笔记");
        boolean save = tagService.saveOrUpdate(tag);
        System.out.println(save);*/
    }


    @Test
    void testBlogMapper(){
       /* List<Blog> blogs = blogMapper.listAdminBlog();
        blogs.forEach(b-> System.out.println(b));*/
        IUserService userService = (IUserService)ApplicationContextUtils.getBean("userService");
        System.out.println(userService.getOne(new QueryWrapper<User>().select("id", "user_name", "password").eq("id",1)));
    }

    @Test
    void testES(){
        /*Blog save = IBlogMapper_es.save(new Blog().setContent("sajdjsad").setUserId(1)
                .setTypeId(1).setTitle("sdsd").setId(1)
                .setUpdateTime(new Date()).setUpdateTime(new Date()).setView(0).setFirstPicture("sdsad")
                .setType(new Type(1,"java",0)));*/
        /*Iterable<Blog> all = IBlogMapper_es.findAll();
        all.forEach(s-> System.out.println(s));*/

        Page<Blog> search = blogMapper_es.search(QueryBuilders.matchQuery("content","文章"), PageRequest.of(1, 2));
        List<Blog> blogs = new ArrayList<>();
        for (Blog blog : search) {

            blog.setUser(userMapper.selectOne(new QueryWrapper<User>().select("id","avatar","nick_name").eq("id",blog.getUserId())));
            blog.setType(typeService.getById(blog.getTypeId()));
            blogs.add(blog);
        }
        blogs.forEach(s-> System.out.println(s));
    }

}
