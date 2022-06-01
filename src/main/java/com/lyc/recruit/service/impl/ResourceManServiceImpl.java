package com.lyc.recruit.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyc.recruit.exception.RecruitException;
import com.lyc.recruit.exception.RecruitExceptionEnum;
import com.lyc.recruit.model.dao.ResourceManMapper;
import com.lyc.recruit.model.pojo.ResourceMan;
import com.lyc.recruit.model.pojo.User;
import com.lyc.recruit.service.ResourceManService;
import com.lyc.recruit.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class ResourceManServiceImpl implements ResourceManService {

    @Autowired
    ResourceManMapper resourceManMapper;


    /**
     * 判断邮箱是否已经注册过
     *
     * @param email 邮箱地址
     * @return 已注册返回false，未注册返回true
     */
    @Override
    public boolean checkEmailRegister(String email) {
        ResourceMan resourceMan = resourceManMapper.selectOneByEmail(email);
        if (resourceMan != null) {
            return false;
        }
        return true;
    }

    /**
     * hr用户注册
     * @param username 用户名
     * @param password 密码
     * @param email 邮箱
     * @throws RecruitException
     */
    @Override
    public void register(String username, String password, String email) throws RecruitException {
        //查询用户名是否存在，不允许重名
        ResourceMan result = resourceManMapper.selectByName(username);
        if (result != null) {
            throw new RecruitException(RecruitExceptionEnum.NAME_EXISTED);
        }
        //通过校验，将数据写到数据库
        ResourceMan resourceMan = new ResourceMan();
        resourceMan.setUsername(username);
        resourceMan.setEmail(email);
        try {
            resourceMan.setPassword(MD5Utils.getMD5Str(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        resourceMan.setNickname(username);
        int count = resourceManMapper.insertSelective(resourceMan);
        if (count == 0) {
            throw new RecruitException(RecruitExceptionEnum.INSERT_FAILED);
        }
    }

    /**
     * hr用户登录
     * @param username 用户名
     * @param password 密码
     * @return resourceMan
     * @throws RecruitException
     */
    @Override
    public ResourceMan login(String username, String password)throws RecruitException{
        String md5Password = null;
        try {
            md5Password = MD5Utils.getMD5Str(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        ResourceMan resourceMan = resourceManMapper.selectLogin(username, md5Password);
        if (resourceMan == null) {
            throw new RecruitException(RecruitExceptionEnum.WRONG_PASSWORD);
        }
        return resourceMan;
    }

    /**
     * 更新hr用户信息
     * @param resourceMan
     * @throws RecruitException
     */
    @Override
    public void updateInfo(ResourceMan resourceMan) throws RecruitException{
        ResourceMan oldResourceMan1 = resourceManMapper.selectByName(resourceMan.getUsername());
        if (oldResourceMan1.getId() != resourceMan.getId()){
            throw new RecruitException(RecruitExceptionEnum.NAME_EXISTED);
        }
        ResourceMan oldResourceMan2 = resourceManMapper.selectOneByEmail(resourceMan.getEmail());
        if (oldResourceMan2.getId() != resourceMan.getId()) {
            throw new RecruitException(RecruitExceptionEnum.EMAIL_ALREADY_BEEN_REGISTERED);
        }
        String md5Password = null;
        try {
            md5Password = MD5Utils.getMD5Str(resourceMan.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        resourceMan.setPassword(md5Password);
        int updateCount = resourceManMapper.updateByPrimaryKeySelective(resourceMan);
        if (updateCount>1){
            throw new RecruitException(RecruitExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public ResourceMan showHrInfo(Integer id){
        ResourceMan resourceMan = resourceManMapper.selectByPrimaryKey(id);
        resourceMan.setPassword(null);
        return resourceMan;
    }

    @Override
    public void updatePassword(String username, String password, String newPassword) {
        String md5Password1 = null;
        try {
            md5Password1 = MD5Utils.getMD5Str(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        ResourceMan resourceMan = resourceManMapper.selectLogin(username, md5Password1);
        if (resourceMan == null) {
            throw new RecruitException(RecruitExceptionEnum.WRONG_PASSWORD);
        }
        String md5Password2 = null;
        try {
            md5Password2 = MD5Utils.getMD5Str(newPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        int updateCount = resourceManMapper.updatePassword(username, md5Password2);
        if (updateCount == 0) {
            throw new RecruitException(RecruitExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public void updateHrCode(Integer id,String code){
        ResourceMan resourceMan = resourceManMapper.selectByPrimaryKey(id);
        if (resourceMan == null) {
            throw  new RecruitException(RecruitExceptionEnum.NOT_EXISTENCE_HR);
        }
        int updateCount = resourceManMapper.updateCode(id, code);
        if (updateCount == 0) {
            throw new RecruitException(RecruitExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public PageInfo selectAllHr(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ResourceMan> resourceManList = resourceManMapper.selectAllResourceMan();
        PageInfo pageInfo = new PageInfo(resourceManList);
        return pageInfo;
    }

    @Override
    public void deleteHrById(Integer id) throws RecruitException{
        int deleteCount = resourceManMapper.deleteByPrimaryKey(id);
        if (deleteCount == 0) {
            throw new RecruitException(RecruitExceptionEnum.DELETE_FAILED);
        }
    }

    @Override
    public PageInfo selectAllByName(String username, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ResourceMan resourceMan = new ResourceMan();
        resourceMan.setUsername(username);
        List<ResourceMan> resourceManList = resourceManMapper.selectAllByUsername(resourceMan);
        PageInfo pageInfo = new PageInfo(resourceManList);
        return pageInfo;
    }
}
