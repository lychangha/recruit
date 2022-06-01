package com.lyc.recruit.service.impl;

import com.lyc.recruit.common.Constant;
import com.lyc.recruit.exception.RecruitException;
import com.lyc.recruit.exception.RecruitExceptionEnum;
import com.lyc.recruit.model.dao.SendMapper;
import com.lyc.recruit.model.pojo.Send;
import com.lyc.recruit.model.request.SendReq;
import com.lyc.recruit.service.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SendServiceImpl implements SendService {

    @Autowired
    SendMapper sendMapper;

    @Override
    public void addSendRecord(Integer resumeId, Integer positionId) throws RecruitException {
        Send oldSend = sendMapper.selectByTwoId(resumeId, positionId);
        if (oldSend != null && (oldSend.getState() == Constant.SendStatusEnum.PROCESS.getCode() || oldSend.getState() == Constant.SendStatusEnum.READY.getCode()
                || oldSend.getState() == Constant.SendStatusEnum.APPROVED.getCode() || oldSend.getState() == Constant.SendStatusEnum.INVITE.getCode())) {
            throw new RecruitException(RecruitExceptionEnum.REPEAT_SEND);
        }
        Send send = new Send();
        send.setResumeId(resumeId);
        send.setPositionId(positionId);
        send.setState(Constant.SendStatusEnum.PROCESS.getCode());
        int addCount = sendMapper.insertSelective(send);
        if (addCount == 0) {
            throw new RecruitException(RecruitExceptionEnum.CREATE_FAILED);
        }
    }

    @Override
    public List<SendReq> selectSendByState(Integer state) {
        List<SendReq> sendReqList = sendMapper.selectSendByState(state);
        return sendReqList;
    }

    @Override
    public void updateSendState(Integer state, Integer id) {
        int updateCount = sendMapper.updateState(state, id);
        if (updateCount == 0) {
            throw new RecruitException(RecruitExceptionEnum.UPDATE_FAILED);
        }
    }
}
