package com.lyc.recruit.controller;

import com.lyc.recruit.common.ApiRestResponse;
import com.lyc.recruit.model.pojo.Collection;
import com.lyc.recruit.model.request.CollectionReq;
import com.lyc.recruit.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CollectionController {

    @Autowired
    CollectionService collectionService;

    /**
     * 收藏职位
     *
     * @param userId
     * @param positionId
     * @return
     */
    @PostMapping("/addCollection")
    @ResponseBody
    public ApiRestResponse addCollection(@RequestParam("userId") Integer userId, @RequestParam("positionId") Integer positionId) {
        collectionService.addCollection(userId, positionId);
        return ApiRestResponse.success();
    }

    /**
     * 取消收藏
     *
     * @param userId
     * @param positionId
     * @return
     */
    @PostMapping("/deleteCollection")
    @ResponseBody
    public ApiRestResponse deleteCollection(@RequestParam("userId") Integer userId, @RequestParam("positionId") Integer positionId) {
        collectionService.deleteCollection(userId, positionId);
        return ApiRestResponse.success();
    }

    /**
     * 根据用户查询所收藏的职位
     *
     * @param userId
     * @return
     */
    @PostMapping("/selectCollection")
    @ResponseBody
    public ApiRestResponse selectCollectionPosition(@RequestParam("userId") Integer userId) {
        List<CollectionReq> collectionReqList = collectionService.selectCollectionPosition(userId);
        return ApiRestResponse.success(collectionReqList);
    }

    /**
     * 查询所有收藏的职位
     * @param userId
     * @param positionId
     * @return
     */
    @PostMapping("/selectIsCollection")
    @ResponseBody
    public ApiRestResponse selectIsCollection(@RequestParam("userId") Integer userId, @RequestParam("positionId") Integer positionId) {
        Collection collection = collectionService.selectIsCollection(userId, positionId);
        return ApiRestResponse.success(collection);
    }


}
