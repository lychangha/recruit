package com.lyc.recruit.service;

import com.lyc.recruit.exception.RecruitException;
import com.lyc.recruit.model.pojo.Collection;
import com.lyc.recruit.model.request.CollectionReq;

import java.util.List;

public interface CollectionService {
    void addCollection(Integer userId, Integer positionId);

    void deleteCollection(Integer userId, Integer positionId) throws RecruitException;

    List<CollectionReq> selectCollectionPosition(Integer userId);

    Collection selectIsCollection(Integer userId, Integer positionId);
}
