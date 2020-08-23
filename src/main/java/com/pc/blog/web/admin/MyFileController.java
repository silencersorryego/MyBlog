package com.pc.blog.web.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pc.blog.domain.Blog;
import com.pc.blog.domain.MyFile;
import com.pc.blog.service.IMyFileService;
import com.pc.blog.utils.QiNiuUtils;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("admin")
public class MyFileController {

    @Autowired
    IMyFileService myFileService;

    @RequestMapping("file")
    public String toFile(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        PageHelper.startPage(pageNum,8);
        List<MyFile> myFiles = myFileService.list();
        PageInfo<MyFile> pageInfo = new PageInfo<>(myFiles);
        model.addAttribute("pageInfo",pageInfo);
        return "admin/my-file";
    }

    @PostMapping("file")
    public String postFile(RedirectAttributes redirectAttributes, @RequestParam("file") MultipartFile file) throws IOException {
        String oldFileName = file.getOriginalFilename();
        MyFile old_File = myFileService.getOne(new QueryWrapper<MyFile>().eq("name", oldFileName));
        if(old_File == null){
            String ext = oldFileName.substring(oldFileName.lastIndexOf("."));
            Long size = file.getSize();
            String contentType = file.getContentType();

            MyFile myFile = new MyFile();
            myFile.setName(oldFileName).setSuffix(ext).setSize(getPrintSize(size))
                .setType(contentType).setUploadTime(new Date());
            QiNiuUtils.uploadQiNiu(file.getBytes(),oldFileName,"silencersorry");
            myFileService.save(myFile);
            redirectAttributes.addFlashAttribute("message","恭喜，文件上传成功！");
        }
        else {
            QiNiuUtils.uploadQiNiu(file.getBytes(),old_File.getName(),"silencersorry");
            redirectAttributes.addFlashAttribute("message","该文件已存在，成功覆盖上传！");
        }
        return  "redirect:/admin/file";
    }

    @RequiresRoles("admin")
    @RequestMapping("file/{id}")
    public String deleteFile(Model model, @PathVariable("id") Integer id){
        myFileService.removeById(id);
        return "redirect:/admin/file";
    }


    public static String getPrintSize(long size) {
        // 如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        double value = (double) size;
        if (value < 1024) {
            return String.valueOf(value) + "B";
        } else {
            value = new BigDecimal(value / 1024).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
        }
        // 如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        // 因为还没有到达要使用另一个单位的时候
        // 接下去以此类推
        if (value < 1024) {
            return String.valueOf(value) + "KB";
        } else {
            value = new BigDecimal(value / 1024).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
        }
        if (value < 1024) {
            return String.valueOf(value) + "MB";
        } else {
            // 否则如果要以GB为单位的，先除于1024再作同样的处理
            value = new BigDecimal(value / 1024).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
            return String.valueOf(value) + "GB";
        }
    }

}
