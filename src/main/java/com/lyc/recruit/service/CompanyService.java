package com.lyc.recruit.service;

import com.lyc.recruit.exception.RecruitException;
import com.lyc.recruit.model.pojo.Company;

public interface CompanyService {
    Company selectCompanyByCode(String code);

    void updateCompanyInfo(Company company);

    void addCompanyInfo(Company company) throws RecruitException;
}
