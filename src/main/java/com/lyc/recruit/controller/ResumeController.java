package com.lyc.recruit.controller;

import com.lyc.recruit.common.ApiRestResponse;
import com.lyc.recruit.model.pojo.Resume;
import com.lyc.recruit.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ResumeController {

    @Autowired
    ResumeService resumeService;

    /**
     * 查询所有的简历
     * @param userId 用户ID
     * @return
     */
    @PostMapping("/selectAllResume")
    @ResponseBody
    public ApiRestResponse selectByUserIdResume(@RequestParam("userId") Integer userId){
        List<Resume> resumeList = resumeService.selectByUserIdResume(userId);
        return ApiRestResponse.success(resumeList);
    }

    /**
     * 新增简历
     * @param resume 简历
     * @return
     */
    @PostMapping("/addResume")
    @ResponseBody
    public ApiRestResponse addResume(@RequestBody Resume resume){
        resumeService.addResume(resume);
        return ApiRestResponse.success();
    }

    /**
     * 根据简历id查询改简历的具体详情
     * @param id 简历id
     * @return
     */
    @PostMapping("/selectResumeDetails")
    @ResponseBody
    public ApiRestResponse selectResumeDetails(@RequestParam Integer id){
        Resume resume = resumeService.selectResumeById(id);
        return ApiRestResponse.success(resume);
    }

    /**
     * 修改简历
     * @param resume 修改后的简历对象
     * @return
     */
    @PostMapping("/edit")
    @ResponseBody
    public ApiRestResponse edit(@RequestBody Resume resume){
        resumeService.updateResume(resume);
        return ApiRestResponse.success();
    }

    /**
     * 删除简历
     * @param id 简历id
     * @return
     */
    @PostMapping("/deleteResume")
    @ResponseBody
    public ApiRestResponse delete(@RequestParam Integer id){
        resumeService.deleteResume(id);
        return ApiRestResponse.success();
    }


}
