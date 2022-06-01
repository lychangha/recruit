package com.lyc.recruit.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyc.recruit.exception.RecruitException;
import com.lyc.recruit.exception.RecruitExceptionEnum;
import com.lyc.recruit.model.dao.UserMapper;
import com.lyc.recruit.model.pojo.User;
import com.lyc.recruit.model.request.PositionCategoryReq;
import com.lyc.recruit.service.UserService;
import com.lyc.recruit.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User getUser() {
        return userMapper.selectByPrimaryKey(1);
    }

    /**
     * 用户注册
     *
     * @param username 用户名
     * @param password 密码
     * @param email    邮箱
     * @throws RecruitException
     */
    @Override
    public void register(String username, String password, String email) throws RecruitException {
        //查询用户名是否存在，不允许重名
        User result = userMapper.selectByName(username);
        if (result != null) {
            throw new RecruitException(RecruitExceptionEnum.NAME_EXISTED);
        }
        //通过校验，将数据写到数据库
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        try {
            user.setPassword(MD5Utils.getMD5Str(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        user.setNickname(username);
        int count = userMapper.insertSelective(user);
        if (count == 0) {
            throw new RecruitException(RecruitExceptionEnum.INSERT_FAILED);
        }
    }

    /**
     * 判断邮箱是否已经注册过
     *
     * @param email 邮箱地址
     * @return 已注册返回false，未注册返回true
     */
    @Override
    public boolean checkEmailRegister(String email) {
        User user = userMapper.selectOneByEmail(email);
        if (user != null) {
            return false;
        }
        return true;
    }

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return user
     * @throws RecruitException
     */
    @Override
    public User login(String username, String password) throws RecruitException {
        String md5Password = null;
        try {
            md5Password = MD5Utils.getMD5Str(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        User user = userMapper.selectLogin(username, md5Password);
        if (user == null) {
            throw new RecruitException(RecruitExceptionEnum.WRONG_PASSWORD);
        }
        return user;
    }


    /**
     * 更新用户昵称
     *
     * @param user
     * @throws RecruitException
     */
    @Override
    public void updateNickname(User user) throws RecruitException {
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if (updateCount > 1) {
            throw new RecruitException(RecruitExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public User showUserInfo(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        user.setPassword(null);
        return user;
    }

    @Override
    public void updateInfo(User user) throws RecruitException {
        User oldUser1 = userMapper.selectByName(user.getUsername());
        if (oldUser1.getId() != user.getId()) {
            throw new RecruitException(RecruitExceptionEnum.NAME_EXISTED);
        }
        User oldUser2 = userMapper.selectOneByEmail(user.getEmail());
        if (oldUser2.getId() != user.getId()) {
            throw new RecruitException(RecruitExceptionEnum.EMAIL_ALREADY_BEEN_REGISTERED);
        }
        String md5Password = null;
        try {
            md5Password = MD5Utils.getMD5Str(user.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        user.setPassword(md5Password);
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if (updateCount == 0) {
            throw new RecruitException(RecruitExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public void updatePassword(String username, String password, String newPassword){
        String md5Password1 = null;
        try {
            md5Password1 = MD5Utils.getMD5Str(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        User user = userMapper.selectLogin(username, md5Password1);
        if (user == null) {
            throw new RecruitException(RecruitExceptionEnum.WRONG_PASSWORD);
        }
        String md5Password2 = null;
        try {
            md5Password2 = MD5Utils.getMD5Str(newPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        int updateCount = userMapper.updatePassword(username, md5Password2);
        if (updateCount == 0) {
            throw new RecruitException(RecruitExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public PageInfo selectAllUser(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userMapper.selectAll();
        PageInfo pageInfo = new PageInfo(userList);
        return pageInfo;
    }

    @Override
    public PageInfo selectAllByName(String username, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        User user = new User();
        user.setUsername(username);
        List<User> userList = userMapper.selectAllByUsername(user);
        PageInfo pageInfo = new PageInfo(userList);
        return pageInfo;
    }

    @Override
    public void deleteById(Integer id) throws RecruitException{
        int deleteCount = userMapper.deleteByPrimaryKey(id);
        if (deleteCount == 0) {
            throw new RecruitException(RecruitExceptionEnum.DELETE_FAILED);
        }
    }
}
