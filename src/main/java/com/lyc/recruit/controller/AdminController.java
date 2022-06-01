package com.lyc.recruit.controller;

import com.lyc.recruit.common.ApiRestResponse;
import com.lyc.recruit.common.Constant;
import com.lyc.recruit.exception.RecruitException;
import com.lyc.recruit.exception.RecruitExceptionEnum;
import com.lyc.recruit.model.pojo.Admin;
import com.lyc.recruit.model.pojo.User;
import com.lyc.recruit.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    AdminService adminService;

    /**
     * 管理员登录
     *
     * @param username 用户名
     * @param password 密码
     * @param session
     * @return
     * @throws RecruitException
     */
    @PostMapping("/admin/login")
    @ResponseBody
    public ApiRestResponse login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) throws RecruitException {
        //判断用户名是否为空
        if (StringUtils.isEmpty(username)) {
            return ApiRestResponse.error(RecruitExceptionEnum.NEED_USER_NAME);
        }
        //判断密码是否为空
        if (StringUtils.isEmpty(password)) {
            return ApiRestResponse.error(RecruitExceptionEnum.NEED_PASSWORD);
        }
        Admin admin = adminService.login(username, password);
        //返回用户信息是不保存密码
        admin.setPassword(null);
        session.setAttribute(Constant.RECRUIT_ADMIN, admin);
        return ApiRestResponse.success(admin);
    }

    /**
     * 管理员登出
     *
     * @param session
     * @return
     */
    @PostMapping("/admin/logout")
    @ResponseBody
    public ApiRestResponse logout(HttpSession session) {
        session.removeAttribute(Constant.RECRUIT_ADMIN);
        return ApiRestResponse.success();
    }
}

