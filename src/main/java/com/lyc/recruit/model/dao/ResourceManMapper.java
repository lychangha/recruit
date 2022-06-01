package com.lyc.recruit.model.dao;

import com.lyc.recruit.model.pojo.ResourceMan;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceManMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ResourceMan record);

    int insertSelective(ResourceMan record);

    ResourceMan selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ResourceMan record);

    int updateByPrimaryKey(ResourceMan record);

    ResourceMan selectOneByEmail(String email);

    ResourceMan selectByName(String username);

    ResourceMan selectLogin(@Param("username") String username, @Param("password") String password);

    List<ResourceMan> selectHrAndCompany(Integer id);

    int updatePassword(@Param("username") String username, @Param("newPassword") String newPassword);

    int updateCode(@Param("id") Integer id,@Param("code")String code);

    List<ResourceMan> selectAllResourceMan();

    List<ResourceMan> selectAllByUsername(ResourceMan resourceMan);
}