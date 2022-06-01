package com.lyc.recruit.service.impl;

import com.lyc.recruit.exception.RecruitException;
import com.lyc.recruit.exception.RecruitExceptionEnum;
import com.lyc.recruit.model.dao.CollectionMapper;
import com.lyc.recruit.model.pojo.Collection;
import com.lyc.recruit.model.request.CollectionReq;
import com.lyc.recruit.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    CollectionMapper collectionMapper;

    /**
     * 收藏
     * @param userId
     * @param positionId
     * @throws RecruitException
     */
    @Override
    public void addCollection(Integer userId, Integer positionId)throws RecruitException{

        Collection collection = new Collection();
        collection.setUserId(userId);
        collection.setPositionId(positionId);
        int addCount = collectionMapper.insertSelective(collection);
        if (addCount == 0) {
            throw new RecruitException(RecruitExceptionEnum.CREATE_FAILED);
        }
    }

    /**
     * 取消收藏
     * @param userId
     * @param positionId
     * @throws RecruitException
     */
    @Override
    public void deleteCollection(Integer userId, Integer positionId) throws  RecruitException{
        Collection collection = collectionMapper.selectByTwoId(userId, positionId);
        if (collection == null) {
            throw new RecruitException(RecruitExceptionEnum.NOT_EXISTENCE_RECORD);
        }
        int deleteCount = collectionMapper.deleteByTwoId(userId, positionId);
        if (deleteCount == 0) {
            throw new RecruitException(RecruitExceptionEnum.DELETE_FAILED);
        }
    }

    /**
     * 查询收藏职位
     * @param userId 用户Id
     * @return
     */
    @Override
    public List<CollectionReq> selectCollectionPosition(Integer userId){
        List<CollectionReq> collectionReqList = collectionMapper.selectCollectionPosition(userId);
        return collectionReqList;
    }

    /**
     * 查询改职位是否被收藏
     * @param userId
     * @param positionId
     * @return
     */
    @Override
    public Collection selectIsCollection(Integer userId,Integer positionId){
        Collection collection = collectionMapper.selectByTwoId(userId, positionId);
        return collection;
    }
}
