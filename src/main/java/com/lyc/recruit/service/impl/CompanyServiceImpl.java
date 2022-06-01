package com.lyc.recruit.service.impl;

import com.lyc.recruit.exception.RecruitException;
import com.lyc.recruit.exception.RecruitExceptionEnum;
import com.lyc.recruit.model.dao.CompanyMapper;
import com.lyc.recruit.model.pojo.Company;
import com.lyc.recruit.service.CompanyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    CompanyMapper companyMapper;

    @Override
    public Company selectCompanyByCode(String code){
        Company company = companyMapper.selectByCode(code);
        return company;
    }

    @Override
    public void updateCompanyInfo(Company company) throws RecruitException{
        int updateCount = companyMapper.updateByPrimaryKeySelective(company);
        if (updateCount == 0) {
            throw new RecruitException(RecruitExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public void addCompanyInfo(Company company) throws RecruitException{
        Company company1 = companyMapper.selectByCode(company.getCode());
        if (company1 != null) {
            throw new RecruitException(RecruitExceptionEnum.CODE_ALREADY_USED);
        }
        int insertCount = companyMapper.insertSelective(company);
        if (insertCount == 0) {
            throw new RecruitException(RecruitExceptionEnum.INSERT_FAILED);
        }
    }
}
