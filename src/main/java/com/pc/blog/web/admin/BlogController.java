package com.pc.blog.web.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pc.blog.domain.Blog;
import com.pc.blog.domain.Tag;
import com.pc.blog.service.IBlogService;
import com.pc.blog.service.ITagService;
import com.pc.blog.service.ITypeService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("admin")
public class BlogController {
    @Autowired
    IBlogService blogService;
    @Autowired
    ITypeService typeService;
    @Autowired
    ITagService tagService;

    @RequestMapping("blog")
    public String blog(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        PageHelper.startPage(pageNum, 4);
        List<Blog> blogs = blogService.listAdminBlog();
        setTypeAndTag(model);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/blog";
    }


    @RequestMapping("blog/input")
    public String inputBlog(Model model,@RequestParam(defaultValue = "0",value = "id") Integer id){
        setTypeAndTag(model);
        return "admin/blog-input";
    }

    @PostMapping("blog")
    public String postBlog(Model model, Blog blog,@RequestParam("picture") MultipartFile file) throws IOException {
        //
        String oldFileName = file.getOriginalFilename();
        String ext = oldFileName.substring(oldFileName.lastIndexOf("."));
        String newFileName = new SimpleDateFormat("yyyy-MMddHHmmss")+ UUID.randomUUID().toString().replace("-","") + ext;
        Long size = file.getSize();
        String contentType = file.getContentType();

        //String datePath = System.getProperty("user.dir") +File.separator+ "static"+File.separator+"file";
        String datePath =  "/opt/uploadfile";

        File dateDir = new File(datePath);

        ////blog.setFirstPicture("/file/"+newFileName);
        blog.setFirstPicture(newFileName);

        if(!dateDir.exists()){
            dateDir.mkdirs();
        }
        file.transferTo(new File(dateDir,newFileName));
        blogService.saveBlog(blog);
        return  "redirect:/admin/blog";
    }

    @RequiresRoles("admin")
    @RequestMapping("blog/delete/{id}")
    public String deleteBlog(@PathVariable("id") Integer id){
        blogService.deleteBlog(id);
        return  "redirect:/admin/blog";
    }

    @PostMapping("/blog/search")
    public String search(Blog searchBlog,Model model,
                         @RequestParam(defaultValue = "0",value = "type_id") Integer typeId,
                         @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum) {

        searchBlog.setTypeId(typeId);
        System.out.println(searchBlog);
        PageHelper.startPage(pageNum, 4);
        List<Blog> blogBySearch = blogService.getBlogBySearch(searchBlog);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogBySearch);
        model.addAttribute("pageInfo", pageInfo);
        setTypeAndTag(model);
        model.addAttribute("message", "查询成功");
        return "admin/blog";
    }




    public void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.list());
        model.addAttribute("tags", tagService.list());
    }
}
