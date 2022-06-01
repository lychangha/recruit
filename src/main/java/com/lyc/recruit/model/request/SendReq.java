package com.lyc.recruit.model.request;

import com.lyc.recruit.model.pojo.Resume;

import java.util.Date;

public class SendReq {
    private Integer id;

    private Integer resumeId;

    private Integer positionId;

    private Integer state;

    private Date createTime;

    private Date updateTime;

    private Resume resume;

    private PositionReq positionReq;

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public PositionReq getPositionReq() {
        return positionReq;
    }

    public void setPositionReq(PositionReq positionReq) {
        this.positionReq = positionReq;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getResumeId() {
        return resumeId;
    }

    public void setResumeId(Integer resumeId) {
        this.resumeId = resumeId;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}