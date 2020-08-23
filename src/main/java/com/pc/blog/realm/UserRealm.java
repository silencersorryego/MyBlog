package com.pc.blog.realm;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pc.blog.domain.User;
import com.pc.blog.service.IUserService;
import com.pc.blog.utils.ApplicationContextUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class UserRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User)principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        if (user.isType()){
            authorizationInfo.addRole("admin");
        }else
        {
            authorizationInfo.addRole("user");
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        IUserService userService = (IUserService)ApplicationContextUtils.getBean("userService");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userService.getOne(new QueryWrapper<User>().select("id","avatar", "nick_name","user_name", "password","type").eq("user_name", token.getPrincipal()));
        if(user != null){
            return new SimpleAuthenticationInfo(user,user.getPassword(), ByteSource.Util.bytes("1234"),getName());
        }
        return null;
    }
}
