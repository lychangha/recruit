package com.lyc.recruit.controller;

import com.github.pagehelper.PageInfo;
import com.lyc.recruit.common.ApiRestResponse;
import com.lyc.recruit.common.Constant;
import com.lyc.recruit.exception.RecruitException;
import com.lyc.recruit.exception.RecruitExceptionEnum;
import com.lyc.recruit.model.pojo.ResourceMan;
import com.lyc.recruit.model.pojo.User;
import com.lyc.recruit.service.EmailService;
import com.lyc.recruit.service.ResourceManService;
import com.lyc.recruit.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * hr Controller
 */
@Controller
public class ResourceManController {

    @Autowired
    ResourceManService resourceManService;

    @Autowired
    EmailService emailService;

    /**
     * hr用户注册
     *
     * @param username         用户名
     * @param password         密码
     * @param email            邮件地址
     * @param verificationCode 验证码
     * @return
     * @throws RecruitException
     */
    @PostMapping("/resource_man/register")
    @ResponseBody
    public ApiRestResponse register(@RequestParam("username") String username, @RequestParam("password") String password,
                                    @RequestParam("email") String email, @RequestParam("verificationCode") String verificationCode) throws RecruitException {
        //判断用户名是否为空
        if (StringUtils.isEmpty(username)) {
            return ApiRestResponse.error(RecruitExceptionEnum.NEED_USER_NAME);
        }
        //判断密码是否为空
        if (StringUtils.isEmpty(password)) {
            return ApiRestResponse.error(RecruitExceptionEnum.NEED_PASSWORD);
        }
        //判断邮箱地址是否为空
        if (StringUtils.isEmpty(password)) {
            return ApiRestResponse.error(RecruitExceptionEnum.NEED_EMAIL_ADDRESS);
        }
        //判断验证码是否为空
        if (StringUtils.isEmpty(password)) {
            return ApiRestResponse.error(RecruitExceptionEnum.NEED_VERIFICATION_CODE);
        }
        //密码长度不得小于8位
        if (password.length() < 8) {
            return ApiRestResponse.error(RecruitExceptionEnum.PASSWORD_TOO_SHORT);
        }
        //如果邮箱已注册，则不允许再次注册
        boolean emailPassed = resourceManService.checkEmailRegister(email);
        if (!emailPassed) {
            return ApiRestResponse.error(RecruitExceptionEnum.EMAIL_ALREADY_BEEN_REGISTERED);
        }
        //校验邮箱和验证码是否匹配
        Boolean passEmailAndCode = emailService.checkEmailAndCode(email, verificationCode);
        if (!passEmailAndCode) {
            return ApiRestResponse.error(RecruitExceptionEnum.WRONG_VERIFICATION_CODE);
        }
        resourceManService.register(username, password, email);
        return ApiRestResponse.success();
    }

    /**
     * 给hr用户发送邮件验证码
     *
     * @param email 邮件地址
     * @return
     * @throws RecruitException
     */
    @PostMapping("/resource_man/sendEmail")
    @ResponseBody
    public ApiRestResponse sendEmail(@RequestParam("email") String email) throws RecruitException {
        //检查邮件地址是否有效
        boolean validEmailAddress = EmailUtil.isValidEmailAddress(email);
        if (validEmailAddress) {
            //检查是否已经注册
            boolean emailPassed = resourceManService.checkEmailRegister(email);
            if (!emailPassed) {
                return ApiRestResponse.error(RecruitExceptionEnum.EMAIL_ALREADY_BEEN_REGISTERED);
            } else {
                String verificationCode = EmailUtil.getVerificationCode();
                Boolean saveEmailToRedis = emailService.saveEmailToRedis(email, verificationCode);
                if (saveEmailToRedis) {
                    emailService.sendSimpleMessage(email, Constant.EMAIL_SUBJECT, "欢迎注册您的验证码是" + verificationCode);
                    return ApiRestResponse.success();
                } else {
                    return ApiRestResponse.error(RecruitExceptionEnum.EMAIL_ALREADY_BEEN_SEND);
                }
            }
        } else {
            return ApiRestResponse.error(RecruitExceptionEnum.WRONG_EMAIL);
        }
    }

    /**
     * hr用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @param session
     * @return
     * @throws RecruitException
     */
    @PostMapping("/resource_man/login")
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
        ResourceMan resourceMan = resourceManService.login(username, password);
        //返回用户信息是不保存密码
        resourceMan.setPassword(null);
        session.setAttribute(Constant.RESOURCE_MAN, resourceMan);
        return ApiRestResponse.success(resourceMan);
    }


    /**
     * 显示Hr的信息
     *
     * @param id
     * @return
     */
    @PostMapping("/resource_man/showHrInfo")
    @ResponseBody
    public ApiRestResponse showHrInfo(@RequestParam Integer id) {
        ResourceMan resourceMan = resourceManService.showHrInfo(id);
        return ApiRestResponse.success(resourceMan);
    }

    /**
     * 更新Hr用户信息
     *
     * @param resourceMan
     * @return
     * @throws RecruitException
     */
    @PostMapping("/resource_man/updateHrInfo")
    @ResponseBody
    public ApiRestResponse updateHrInfo(@RequestBody ResourceMan resourceMan) throws RecruitException {
        resourceManService.updateInfo(resourceMan);
        return ApiRestResponse.success();
    }

    @PostMapping("/resource_man/updatePassword")
    @ResponseBody
    public ApiRestResponse updatePassword(@RequestParam("username") String username,
                                          @RequestParam("password") String password, @RequestParam("newPassword") String newPassword) {
        resourceManService.updatePassword(username, password, newPassword);
        return ApiRestResponse.success();
    }

    @PostMapping("/resource_man/updateHrCode")
    @ResponseBody
    public ApiRestResponse updateHrCode(@RequestParam("id") Integer id, @RequestParam("code") String code) {
        resourceManService.updateHrCode(id, code);
        return ApiRestResponse.success();
    }

    /**
     * 用户登出
     *
     * @param session
     * @return
     */
    @PostMapping("/resource_man/logout")
    @ResponseBody
    public ApiRestResponse logout(HttpSession session) {
        session.removeAttribute(Constant.RESOURCE_MAN);
        return ApiRestResponse.success();
    }

    @PostMapping("/resource_man/showAllHr")
    @ResponseBody
    public ApiRestResponse showAllHr(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        PageInfo pageInfo = resourceManService.selectAllHr(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    @PostMapping("/resource_man/deleteHrById")
    @ResponseBody
    public ApiRestResponse deleteHrById(Integer id) {
        resourceManService.deleteHrById(id);
        return ApiRestResponse.success();
    }

    @PostMapping("/resource_man/selectByName")
    @ResponseBody
    public ApiRestResponse selectByName(@RequestParam("username") String username,@RequestParam("pageNum")Integer pageNum,@RequestParam("pageSize") Integer pageSize){
        PageInfo pageInfo = resourceManService.selectAllByName(username, pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }
}
