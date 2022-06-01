package com.lyc.recruit.model.dao;

import com.lyc.recruit.model.pojo.Collection;
import com.lyc.recruit.model.request.CollectionReq;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Collection record);

    int insertSelective(Collection record);

    Collection selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Collection record);

    int updateByPrimaryKey(Collection record);

    Collection selectByTwoId(@Param("userId") Integer userId, @Param("positionId") Integer positionId);

    int deleteByTwoId(@Param("userId") Integer userId, @Param("positionId") Integer positionId);

    List<CollectionReq> selectCollectionPosition(Integer userId);

    int deleteByPositionId(Integer positionId);
}