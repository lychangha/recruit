package com.lyc.recruit.service.impl;

import com.lyc.recruit.exception.RecruitException;
import com.lyc.recruit.exception.RecruitExceptionEnum;
import com.lyc.recruit.model.dao.ResumeMapper;
import com.lyc.recruit.model.pojo.Resume;
import com.lyc.recruit.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    ResumeMapper resumeMapper;

    /**
     * 更具当前用户查询所有的简历
     * @param userId
     * @return
     */
    @Override
    public List<Resume> selectByUserIdResume(Integer userId){
        List<Resume> resumeList = resumeMapper.selectByUserIdResume(userId);
        return resumeList;
    }

    /**
     * 新增简历
     * @param resume
     * @throws RecruitException
     */
    @Override
    public void addResume(Resume resume)throws RecruitException{
        Resume OldResume = resumeMapper.selectByTitle(resume.getTitle());
        if (OldResume != null) {
            throw new RecruitException(RecruitExceptionEnum.NAME_EXISTED);
        }
        int insertCount = resumeMapper.insertSelective(resume);
        if (insertCount == 0) {
            throw new RecruitException(RecruitExceptionEnum.CREATE_FAILED);
        }
    }

    /**
     * 根据简历id查询简历详情
     * @param id
     * @return
     */
    @Override
    public Resume selectResumeById(Integer id){
        Resume resume = resumeMapper.selectByPrimaryKey(id);
        return resume;
    }

    /**
     * 更新简历
     * @param resume
     * @throws RecruitException
     */
    @Override
    public void updateResume(Resume resume)throws RecruitException{
        int updateCount = resumeMapper.updateByPrimaryKeySelective(resume);
        if (updateCount == 0) {
            throw new RecruitException(RecruitExceptionEnum.UPDATE_FAILED);
        }
    }

    /**
     * 删除简历
     * @param id
     * @throws RecruitException
     */
    @Override
    public void deleteResume(Integer id)throws RecruitException{
        Resume resume = resumeMapper.selectByPrimaryKey(id);
        if (resume == null) {
            throw new RecruitException(RecruitExceptionEnum.NOT_EXISTENCE_RESUME);
        }
        int deleteCount = resumeMapper.deleteByPrimaryKey(id);
        if (deleteCount == 0){
            throw new RecruitException(RecruitExceptionEnum.DELETE_FAILED);
        }
    }


}
