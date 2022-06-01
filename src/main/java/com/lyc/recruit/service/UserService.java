package com.lyc.recruit.service;

import com.github.pagehelper.PageInfo;
import com.lyc.recruit.exception.RecruitException;
import com.lyc.recruit.model.pojo.User;

public interface UserService {

    User getUser();

    void register(String username, String password, String email);

    boolean checkEmailRegister(String email);

    User login(String username, String password);

    void updateNickname(User user);

    User showUserInfo(Integer userId);

    void updateInfo(User user) throws RecruitException;

    void updatePassword(String username, String password, String newPassword);

    PageInfo selectAllUser(Integer pageNum, Integer pageSize);

    PageInfo selectAllByName(String username, Integer pageNum, Integer pageSize);

    void deleteById(Integer id) throws RecruitException;
}
