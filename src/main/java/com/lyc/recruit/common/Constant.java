package com.lyc.recruit.common;

import com.lyc.recruit.exception.RecruitException;
import com.lyc.recruit.exception.RecruitExceptionEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 常量类
 */
@Component
public class Constant {
    public final static String RECRUIT_USER ="recruit_user";
    public final static String RESOURCE_MAN ="resource_man";
    public final static String RECRUIT_ADMIN ="recruit_admin";
    public final static String SALT = "7fdsh9JHf9g1.[";
    public final static String EMAIL_SUBJECT = "您的验证码";
    public final static String EMAIL_FROM = "3440296882@qq.com";


    public static String FILE_UPLOAD_DIR;

    @Value("${file.upload.dir}")
    public void setFileUploadDir(String fileUploadDir) {
        FILE_UPLOAD_DIR = fileUploadDir;
    }


    public enum SendStatusEnum {
        PROCESS(0, "待查看")
        , READY(10, "以查看")
        , APPROVED(20, "通过初筛")
        , INVITE(30, "邀请面试")
        , PASS(40, "通过面试")
        , FAIL(50, "不合适");

        private int code;
        private String value;

        SendStatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public static SendStatusEnum codeOf(int code){
            for (SendStatusEnum sendStatusEnum :values()){
                if (sendStatusEnum.getCode() == code){
                    return sendStatusEnum;
                }
            }
            throw new RecruitException(RecruitExceptionEnum.NO_ENUM);
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
