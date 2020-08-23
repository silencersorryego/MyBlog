package com.pc.blog.web.admin;

import com.pc.blog.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("admin")
public class UserController {


    @RequestMapping("toLogin")
    public String login(){
        return "admin/login";
    }

    @RequestMapping("login")
    public String login(Model model, User user, HttpSession httpSession){
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(),user.getPassword());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        }catch (UnknownAccountException e){
            model.addAttribute("message","用户名错误");
            return "admin/login";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("message","密码错误");
            return "admin/login";
        }
        User user1 =(User) subject.getPrincipal();
        user1.setPassword(null);
        httpSession.setAttribute("user",user1);
        return "redirect:/admin/blog";
    }

    @RequestMapping("logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/admin/toLogin";
    }

}
