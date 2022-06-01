package com.lyc.recruit.controller;

import com.github.pagehelper.PageInfo;
import com.lyc.recruit.common.ApiRestResponse;
import com.lyc.recruit.common.Constant;
import com.lyc.recruit.exception.RecruitException;
import com.lyc.recruit.exception.RecruitExceptionEnum;
import com.lyc.recruit.model.pojo.User;
import com.lyc.recruit.service.EmailService;
import com.lyc.recruit.service.UserService;
import com.lyc.recruit.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 普通用户Controller
 */
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;

    @GetMapping("/test")
    @ResponseBody
    public User personalPage() {
        return userService.getUser();
    }

    /**
     * 用户注册
     *
     * @param username         用户名
     * @param password         密码
     * @param email            邮件地址
     * @param verificationCode 验证码
     * @return
     * @throws RecruitException
     */
    @PostMapping("/user/register")
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
        boolean emailPassed = userService.checkEmailRegister(email);
        if (!emailPassed) {
            return ApiRestResponse.error(RecruitExceptionEnum.EMAIL_ALREADY_BEEN_REGISTERED);
        }
        //校验邮箱和验证码是否匹配
        Boolean passEmailAndCode = emailService.checkEmailAndCode(email, verificationCode);
        if (!passEmailAndCode) {
            return ApiRestResponse.error(RecruitExceptionEnum.WRONG_VERIFICATION_CODE);
        }
        userService.register(username, password, email);
        return ApiRestResponse.success();
    }

    /**
     * 给普通用户发送邮件验证码
     *
     * @param email 邮件地址
     * @return
     * @throws RecruitException
     */
    @PostMapping("/user/sendEmail")
    @ResponseBody
    public ApiRestResponse sendEmail(@RequestParam(value = "email") String email) throws RecruitException {
        //检查邮件地址是否有效
        boolean validEmailAddress = EmailUtil.isValidEmailAddress(email);
        if (validEmailAddress) {
            //检查是否已经注册
            boolean emailPassed = userService.checkEmailRegister(email);
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
     * 用户登录
     *
     * @param username
     * @param password
     * @param session
     * @return
     * @throws RecruitException
     */
    @PostMapping("/user/login")
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
        User user = userService.login(username, password);
        //返回用户信息是不保存密码
        user.setPassword(null);
        session.setAttribute(Constant.RECRUIT_USER, user);
        return ApiRestResponse.success(user);
    }

    /**
     * 用户修改昵称
     *
     * @param session
     * @param nickname 昵称
     * @return
     * @throws RecruitException
     */
    @PostMapping("/user/update_nickname")
    @ResponseBody
    public ApiRestResponse updateNickname(HttpSession session, @RequestParam("nickname") String nickname) throws RecruitException {
        User currentUser = (User) session.getAttribute(Constant.RECRUIT_USER);
        if (currentUser == null) {
            return ApiRestResponse.error(RecruitExceptionEnum.NEED_LOGIN);
        }
        User user = new User();
        user.setId(currentUser.getId());
        user.setNickname(nickname);
        userService.updateNickname(user);
        return ApiRestResponse.success();
    }

    /**
     * 显示用户信息
     * @param userId
     * @return
     */
    @PostMapping("/user/showUserInfo")
    @ResponseBody
    public ApiRestResponse showUserInfo(@RequestParam("userId") Integer userId){
        User user = userService.showUserInfo(userId);
        return ApiRestResponse.success(user);
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @PostMapping("/user/updateInfo")
    @ResponseBody
    public ApiRestResponse updateInfo(@RequestBody User user){
        userService.updateInfo(user);
        return ApiRestResponse.success();
    }

    /**
     * 更新密码
     * @param username 用户名
     * @param password 旧密码
     * @param newPassword 新密码
     * @return
     */
    @PostMapping("/user/updatePassword")
    @ResponseBody
    public ApiRestResponse updatePassword(@RequestParam("username") String username,
                                          @RequestParam("password")String password,@RequestParam("newPassword")String newPassword){
        userService.updatePassword(username,password,newPassword);
        return ApiRestResponse.success();
    }


    /**
     * 用户登出
     * @param session
     * @return
     */
    @PostMapping("/user/logout")
    @ResponseBody
    public ApiRestResponse logout(HttpSession session) {
        session.removeAttribute(Constant.RECRUIT_USER);
        return ApiRestResponse.success();
    }

    @PostMapping("/user/selectAllUser")
    @ResponseBody
    public ApiRestResponse selectAllUser(@RequestParam("pageNum")Integer pageNum,@RequestParam("pageSize") Integer pageSize){
        PageInfo pageInfo = userService.selectAllUser(pageNum,pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    @PostMapping("/user/selectByName")
    @ResponseBody
    public ApiRestResponse selectByName(@RequestParam("username") String username,@RequestParam("pageNum")Integer pageNum,@RequestParam("pageSize") Integer pageSize){
        PageInfo pageInfo = userService.selectAllByName(username, pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    @PostMapping("/user/deleteById")
    @ResponseBody
    public ApiRestResponse deleteById(@RequestParam Integer id){
        userService.deleteById(id);
        return ApiRestResponse.success();
    }

}
