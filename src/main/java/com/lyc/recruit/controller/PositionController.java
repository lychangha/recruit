package com.lyc.recruit.controller;

import com.github.pagehelper.PageInfo;
import com.lyc.recruit.common.ApiRestResponse;
import com.lyc.recruit.common.Constant;
import com.lyc.recruit.exception.RecruitException;
import com.lyc.recruit.exception.RecruitExceptionEnum;
import com.lyc.recruit.model.pojo.Position;
import com.lyc.recruit.model.request.Condition;
import com.lyc.recruit.model.request.Conditions;
import com.lyc.recruit.model.request.PositionReq;
import com.lyc.recruit.model.request.PositionVO;
import com.lyc.recruit.service.PositionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class PositionController {

    @Autowired
    PositionService positionService;



    /**
     * 图片上传
     * @param httpServletRequest
     * @param file
     * @return
     */
    @PostMapping("/upload/file")
    @ResponseBody
    public ApiRestResponse upload(HttpServletRequest httpServletRequest, @RequestParam("file") MultipartFile file) {
        //获取原始的名字
        String fileName = file.getOriginalFilename();
        //后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //生成文件名称UUID
        UUID uuid = UUID.randomUUID();
        String newFileName = uuid.toString() + suffixName;
        //创建文件夹
        File fileDirectory = new File(Constant.FILE_UPLOAD_DIR);
        //目标文件
        File destFile = new File(Constant.FILE_UPLOAD_DIR + newFileName);
        if (!fileDirectory.exists()) {
            if (!fileDirectory.mkdir()) {
                throw new RecruitException(RecruitExceptionEnum.MKDIR_FAILED);
            }
        }
        try {
            //把传进来的file写到空的destFile中
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return ApiRestResponse.success(getHost(new URI(httpServletRequest.getRequestURL() + "")) + "/images/" + newFileName);
        } catch (URISyntaxException e) {
            return ApiRestResponse.error(RecruitExceptionEnum.UPLOAD_FAILED);
        }
    }

    /**
     * WangEditor 上传图片
     * @param httpServletRequest
     * @param file
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/upload/editorFile")
    @ResponseBody
    public Map editorFile(HttpServletRequest httpServletRequest, @RequestParam("file") MultipartFile file) throws URISyntaxException {
        //获取原始的名字
        String fileName = file.getOriginalFilename();
        //后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //生成文件名称UUID
        UUID uuid = UUID.randomUUID();
        String newFileName = uuid.toString() + suffixName;
        //创建文件夹
        File fileDirectory = new File(Constant.FILE_UPLOAD_DIR);
        //目标文件
        File destFile = new File(Constant.FILE_UPLOAD_DIR + newFileName);
        if (!fileDirectory.exists()) {
            if (!fileDirectory.mkdir()) {
                throw new RecruitException(RecruitExceptionEnum.MKDIR_FAILED);
            }
        }
        try {
            //把传进来的file写到空的destFile中
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map result = new LinkedHashMap();
        result.put("errno", 0);
        result.put("data", new String[]{getHost(new URI(httpServletRequest.getRequestURL() + "")) + "/images/" + newFileName});
        return result;
    }

    private URI getHost(URI uri) {
        URI effectiveURI;
        try {
            effectiveURI = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(),
                    uri.getPort(), null, null, null);
        } catch (URISyntaxException e) {
            effectiveURI = null;
        }
        return effectiveURI;
    }

    /**
     * 按点击量对职位进行降序排列
     * @return
     */
    @PostMapping("/selectByHits")
    @ResponseBody
    public ApiRestResponse selectPositionByHits(){
        List<PositionReq> positionReqList = positionService.selectPositionByHits();
        return ApiRestResponse.success(positionReqList);
    }

    /**
     * 按发布时间进行降序排列
     * @return
     */
    @PostMapping("/selectByRelease")
    @ResponseBody
    public ApiRestResponse selectPositionByRelease(){
        List<PositionReq> positionReqList = positionService.selectPositionByRelease();
        return ApiRestResponse.success(positionReqList);
    }

    /**
     * 点击职位展示职位的详细情况
     * @param id 职位id
     * @return
     */
    @PostMapping("/details")
    @ResponseBody
    public ApiRestResponse details(@RequestParam Integer id){
        PositionReq details = positionService.details(id);
        return ApiRestResponse.success(details);
    }

    /**
     * 增加点击量
     * @param id
     * @return
     */
    @PostMapping("/addHits")
    @ResponseBody
    public ApiRestResponse addHits(@RequestParam Integer id){
        positionService.addHits(id);
        return ApiRestResponse.success();
    }

    @PostMapping("/selectByHr")
    @ResponseBody
    public ApiRestResponse selectByHr(@RequestParam("resourceManId") Integer resourceManId,@RequestParam("pageNum") Integer pageNum,@RequestParam("pageSize") Integer pageSize){
        PageInfo pageInfo = positionService.selectByHr(resourceManId, pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    @PostMapping("/addPosition")
    @ResponseBody
    public ApiRestResponse addPosition(@RequestBody Position position){
        positionService.addPosition(position);
        return ApiRestResponse.success();
    }

    @PostMapping("/updatePosition")
    @ResponseBody
    public ApiRestResponse updatePosition(@RequestBody Position position){
        positionService.updatePosition(position);
        return ApiRestResponse.success();
    }
    @PostMapping("/deletePosition")
    @ResponseBody
    public ApiRestResponse deletePosition(@RequestParam Integer id){
        positionService.deletePosition(id);
        return ApiRestResponse.success();
    }

    @PostMapping ("/searchByConditions")
    @ResponseBody
    public ApiRestResponse searchByConditions(@RequestBody Conditions conditions){
        Integer pageNum = conditions.getCurrentPage();
        Integer pageSize = conditions.getPageSize();
        conditions.setPageSize(null);
        conditions.setCurrentPage(null);
        Condition condition = new Condition();
        BeanUtils.copyProperties(conditions,condition);
        PageInfo pageInfo = positionService.searchByConditions(condition, pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }

}
