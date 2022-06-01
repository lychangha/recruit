package com.lyc.recruit.service.impl;

import com.lyc.recruit.exception.RecruitException;
import com.lyc.recruit.exception.RecruitExceptionEnum;
import com.lyc.recruit.model.dao.AdminMapper;
import com.lyc.recruit.model.pojo.Admin;
import com.lyc.recruit.model.pojo.User;
import com.lyc.recruit.service.AdminService;
import com.lyc.recruit.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;
    @Override
    public Admin login(String username, String password)throws RecruitException {
        String md5Password = null;
        try {
            md5Password = MD5Utils.getMD5Str(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Admin admin = adminMapper.selectLogin(username, md5Password);
        if (admin == null) {
            throw new RecruitException(RecruitExceptionEnum.WRONG_PASSWORD);
        }
        return admin;
    }
}
