package com.lyc.recruit.service;

import com.github.pagehelper.PageInfo;
import com.lyc.recruit.exception.RecruitException;
import com.lyc.recruit.model.pojo.ResourceMan;

public interface ResourceManService {
    boolean checkEmailRegister(String email);

    void register(String username, String password, String email) throws RecruitException;

    ResourceMan login(String username, String password)throws RecruitException;

    void updateInfo(ResourceMan resourceMan) throws RecruitException;

    ResourceMan showHrInfo(Integer id);

    void updatePassword(String username, String password, String newPassword);

    void updateHrCode(Integer id, String code);

    PageInfo selectAllHr(Integer pageNum, Integer pageSize);

    void deleteHrById(Integer id) throws RecruitException;

    PageInfo selectAllByName(String username, Integer pageNum, Integer pageSize);
}
