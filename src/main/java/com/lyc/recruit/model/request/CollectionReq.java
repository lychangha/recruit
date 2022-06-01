package com.lyc.recruit.model.request;

import java.util.Date;
import java.util.List;

public class CollectionReq {
    private Integer id;

    private Integer userId;

    private Integer positionId;

    private Date createTime;

    private Date updateTime;

    private PositionReq positionReq;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
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