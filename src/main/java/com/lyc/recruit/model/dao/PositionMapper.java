package com.lyc.recruit.model.dao;

import com.lyc.recruit.model.pojo.Position;
import com.lyc.recruit.model.request.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Position record);

    int insertSelective(Position record);

    Position selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Position record);

    int updateByPrimaryKey(Position record);

    List<PositionReq> selectPositionAndHrAndCompanyByHits();

    List<PositionReq> selectPositionAndHrAndCompanyByRelease();

    PositionReq detail(Integer id);

    List<PositionReq> selectById(Integer id);

    List<PositionCategoryReq> selectByHr(Integer resourceManId);

    List<PositionVO> searchByConditions(Condition condition);
}