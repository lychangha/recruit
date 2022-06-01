package com.lyc.recruit.model.dao;

import com.lyc.recruit.model.pojo.Resume;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Resume record);

    int insertSelective(Resume record);

    Resume selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Resume record);

    int updateByPrimaryKey(Resume record);

    List<Resume> selectByUserIdResume(Integer userId);

    Resume selectByTitle(String title);


}