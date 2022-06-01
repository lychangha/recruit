package com.lyc.recruit.service;

import com.lyc.recruit.model.request.SendReq;

import java.util.List;

public interface SendService {

    void addSendRecord(Integer resumeId, Integer positionId);

    List<SendReq> selectSendByState(Integer state);

    void updateSendState(Integer state, Integer id);
}
