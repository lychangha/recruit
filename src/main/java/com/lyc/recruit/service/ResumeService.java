package com.lyc.recruit.service;

import com.lyc.recruit.exception.RecruitException;
import com.lyc.recruit.model.pojo.Resume;


import java.util.List;

public interface ResumeService {

    List<Resume> selectByUserIdResume(Integer userId);

    void addResume(Resume resume)throws RecruitException;

    Resume selectResumeById(Integer id);

    void updateResume(Resume resume)throws RecruitException;

    void deleteResume(Integer id)throws RecruitException;


}
