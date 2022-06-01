package com.lyc.recruit.model.dao;

import com.lyc.recruit.model.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User user);

    int updateByPrimaryKey(User record);

    User selectByName(String username);

    User selectOneByEmail(String email);

    User selectLogin(@Param("username") String username, @Param("password") String password);

    int updatePassword(@Param("username") String username,@Param("newPassword") String newPassword);

    List<User> selectAll();

    List<User> selectAllByUsername(User user);
}