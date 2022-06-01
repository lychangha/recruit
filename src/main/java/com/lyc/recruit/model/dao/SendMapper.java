package com.lyc.recruit.model.dao;

import com.lyc.recruit.model.pojo.Send;
import com.lyc.recruit.model.request.SendReq;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SendMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Send record);

    int insertSelective(Send record);

    Send selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Send record);

    int updateByPrimaryKey(Send record);

    Send selectByTwoId(Integer resumeId, Integer positionId);

    List<SendReq> selectSendByState(Integer state);

    int deleteByPositionId(Integer positionId);

    int updateState(@Param("state") Integer state, @Param("id") Integer id);
}