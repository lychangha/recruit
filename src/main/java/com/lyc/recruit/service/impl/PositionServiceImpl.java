package com.lyc.recruit.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyc.recruit.exception.RecruitException;
import com.lyc.recruit.exception.RecruitExceptionEnum;
import com.lyc.recruit.model.dao.CollectionMapper;
import com.lyc.recruit.model.dao.PositionMapper;
import com.lyc.recruit.model.dao.SendMapper;
import com.lyc.recruit.model.pojo.Position;
import com.lyc.recruit.model.request.*;
import com.lyc.recruit.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {
    @Autowired
    PositionMapper positionMapper;

    @Autowired
    CollectionMapper collectionMapper;

    @Autowired
    SendMapper sendMapper;

    @Override
    public List<PositionReq> selectPositionByHits() {
        List<PositionReq> positionReqList = positionMapper.selectPositionAndHrAndCompanyByHits();
        return positionReqList;
    }

    @Override
    public List<PositionReq> selectPositionByRelease() {
        List<PositionReq> positionReqList = positionMapper.selectPositionAndHrAndCompanyByRelease();
        return positionReqList;
    }

    @Override
    public PositionReq details(Integer id) {
        PositionReq detail = positionMapper.detail(id);
        return detail;
    }

    @Override
    public void addHits(Integer id) {
        Position position = positionMapper.selectByPrimaryKey(id);
        if (position == null) {
            throw new RecruitException(RecruitExceptionEnum.NOT_EXISTENCE);
        }
        position.setHits(position.getHits() + 1);
        positionMapper.updateByPrimaryKeySelective(position);
    }

    @Override
    public PageInfo selectByHr(Integer resourceManId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<PositionCategoryReq> positionCategoryReqList = positionMapper.selectByHr(resourceManId);
        PageInfo pageInfo = new PageInfo(positionCategoryReqList);
        return pageInfo;
    }

    @Override
    public void addPosition(Position position) throws RecruitException {
        int insertCount = positionMapper.insertSelective(position);
        if (insertCount == 0) {
            throw new RecruitException(RecruitExceptionEnum.CREATE_FAILED);
        }
    }

    @Override
    public void updatePosition(Position position) {
        int updateCount = positionMapper.updateByPrimaryKeySelective(position);
        if (updateCount == 0) {
            throw new RecruitException(RecruitExceptionEnum.UPDATE_FAILED);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deletePosition(Integer id) throws RecruitException {
        Position position = positionMapper.selectByPrimaryKey(id);
        if (position == null) {
            throw new RecruitException(RecruitExceptionEnum.NOT_EXISTENCE_RECORD);
        }
        positionMapper.deleteByPrimaryKey(id);
        sendMapper.deleteByPositionId(id);
        collectionMapper.deleteByPositionId(id);
    }

    @Override
    public PageInfo searchByConditions(Condition condition,Integer pageNum,Integer pageSize){
        condition.setRank("`"+condition.getRank()+"`");
        PageHelper.startPage(pageNum, pageSize);
        List<PositionVO> positionVOList = positionMapper.searchByConditions(condition);
        PageInfo pageInfo = new PageInfo(positionVOList);
        return pageInfo;
    }

}
