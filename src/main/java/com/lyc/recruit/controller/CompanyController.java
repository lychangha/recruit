package com.lyc.recruit.controller;

import com.lyc.recruit.common.ApiRestResponse;
import com.lyc.recruit.model.pojo.Company;
import com.lyc.recruit.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @PostMapping("/selectCompanyInfo")
    @ResponseBody
    public ApiRestResponse selectCompanyInfo(@RequestParam String code){
        Company company = companyService.selectCompanyByCode(code);
        return ApiRestResponse.success(company);
    }

    @PostMapping("/updateCompanyInfo")
    @ResponseBody
    public ApiRestResponse updateCompanyInfo(@RequestBody Company company){
        companyService.updateCompanyInfo(company);
        return ApiRestResponse.success();
    }
    @PostMapping("/addCompanyInfo")
    @ResponseBody
    public ApiRestResponse addCompanyInfo(@RequestBody Company company){
        companyService.addCompanyInfo(company);
        return ApiRestResponse.success();
    }
}
