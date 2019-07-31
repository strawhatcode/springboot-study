package com.example.shiro.controller;

import com.example.shiro.bean.User;
import com.example.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @GetMapping("login")
    public String init(){
        return "/login";
    }

    //所有用户都可以访问login页面
    @PostMapping("/login")
    public String login(@RequestParam String user_name,
                        @RequestParam String password){
        UsernamePasswordToken token = new UsernamePasswordToken(user_name, password,true);

        Subject subject = SecurityUtils.getSubject();
        System.out.println("sssssssssssssssssssssession====>"+subject.getSession());
        try {
            subject.login(token);
        } catch (UnknownAccountException e){
            return "用户不存在";
        } catch (IncorrectCredentialsException e){
            return "密码错误";
        } catch (LockedAccountException e){
            return "帐号被锁定";
        } catch (ExcessiveAttemptsException e){
            return "用户名或密码错误次数过多";
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "用户名或密码不正确";
        }

        if (!subject.isAuthenticated()){
            log.info("登录失败");
            return "登录失败";
        }else {
//            token.clear();
            log.info("登录成功");
            return "["+token.getPrincipal()+"]登录成功";
        }
    }


    //要求要有【manager:test】权限的用户才可以进入/manager/aaa页面
    @GetMapping("/manager/aaa")
    @RequiresPermissions("manager:test")
    public String manager(){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return user.getUserName()+"]进入manager";
    }

    //要求有examer角色的用户才可以进入/examer页面
    @RequiresRoles("examer")
    @GetMapping("/examer")
    public String examer(){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return user.getUserName()+"]进入审核页面";
    }
}
