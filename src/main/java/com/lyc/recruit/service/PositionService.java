package com.lyc.recruit.service;

import com.github.pagehelper.PageInfo;
import com.lyc.recruit.exception.RecruitException;
import com.lyc.recruit.model.pojo.Position;
import com.lyc.recruit.model.request.Condition;
import com.lyc.recruit.model.request.Conditions;
import com.lyc.recruit.model.request.PositionReq;
import com.lyc.recruit.model.request.PositionVO;

import java.util.List;

public interface PositionService {

    List<PositionReq> selectPositionByHits();

    List<PositionReq> selectPositionByRelease();

    PositionReq details(Integer id);

    void addHits(Integer id);

    PageInfo selectByHr(Integer resourceManId, Integer pageNum, Integer pageSize);

    void addPosition(Position position)throws RecruitException;

    void updatePosition(Position position);

    void deletePosition(Integer id) throws RecruitException;


    PageInfo searchByConditions(Condition condition, Integer pageNum, Integer pageSize);
}
