package com.lyc.recruit.service;

import com.lyc.recruit.exception.RecruitException;
import com.lyc.recruit.model.pojo.Admin;
import com.lyc.recruit.model.pojo.User;

public interface AdminService {
    Admin login(String username, String password);
}
