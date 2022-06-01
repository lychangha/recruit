package com.lyc.recruit.controller;

import com.lyc.recruit.common.ApiRestResponse;
import com.lyc.recruit.model.request.SendReq;
import com.lyc.recruit.service.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SendController {

    @Autowired
    SendService sendService;

    /**
     * 投递简历
     *
     * @param resumeId
     * @param positionId
     * @return
     */
    @PostMapping("/addSendRecord")
    @ResponseBody
    public ApiRestResponse addSendRecord(@RequestParam("resumeId") Integer resumeId, @RequestParam("positionId") Integer positionId) {
        sendService.addSendRecord(resumeId, positionId);
        return ApiRestResponse.success();
    }

    /**
     * 根据投递状态查询投递的简历
     *
     * @param state
     * @return
     */
    @PostMapping("/selectSendByState")
    @ResponseBody
    public ApiRestResponse selectSendByState(@RequestParam("state") Integer state) {
        List<SendReq> sendReqList = sendService.selectSendByState(state);
        return ApiRestResponse.success(sendReqList);
    }

    /**
     * 根据id更新简历投递状态
     * @param state
     * @param id
     * @return
     */
    @PostMapping("/updateSendState")
    @ResponseBody
    public ApiRestResponse updateSendState(@RequestParam("state") Integer state, @RequestParam("id") Integer id) {
        sendService.updateSendState(state, id);
        return ApiRestResponse.success();
    }
}
