package com.lyc.recruit.model.dao;

import com.lyc.recruit.model.pojo.Company;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Company record);

    int insertSelective(Company record);

    Company selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Company record);

    int updateByPrimaryKey(Company record);

    Company selectByCode(String code);
}