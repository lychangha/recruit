package com.lyc.recruit.exception;

/**
 * 异常枚举
 */
public enum RecruitExceptionEnum {

    NEED_USER_NAME(10001, "用户名不能为空")
    ,NEED_PASSWORD(10002,"密码不能为空")
    ,PASSWORD_TOO_SHORT(10003,"密码长度不能小于8位")
    ,NAME_EXISTED(10004,"不允许重名")
    ,INSERT_FAILED(10005,"插入失败，请重试")
    ,WRONG_EMAIL(10006,"非法的邮件地址")
    ,EMAIL_ALREADY_BEEN_REGISTERED(10007,"email地址已被注册")
    ,EMAIL_ALREADY_BEEN_SEND(10008,"email已发送，若无法收到，请稍后再试")
    ,NEED_EMAIL_ADDRESS(10009,"email不能为空")
    ,NEED_VERIFICATION_CODE(10010,"验证码不能为空")
    ,WRONG_VERIFICATION_CODE(10011,"验证码错误")
    ,WRONG_PASSWORD(10012,"用户名或密码错误")
    ,NEED_LOGIN(10013,"用户未登录")
    ,UPDATE_FAILED(10014,"更新失败")
    ,MKDIR_FAILED(10015,"文件夹创建失败")
    ,UPLOAD_FAILED(10016,"图片上传失败")
    ,NOT_EXISTENCE(10017,"该职位不存在")
    ,CREATE_FAILED(10018,"新增失败")
    ,DELETE_FAILED(10019,"删除失败")
    ,NOT_EXISTENCE_RESUME(10020,"该简历不存在")
    ,NO_ENUM(10021,"未找到对应的枚举类")
    ,REPEAT_SEND(10022,"已经投递过了，不要重复投递")
    ,NOT_EXISTENCE_RECORD(10023,"该记录不存在")
    ,CODE_ALREADY_USED(10024,"唯一编码已存在，请更换重试")
    ,NOT_EXISTENCE_HR(10025,"该用户不存在")
    ,NOT_EXISTENCED(10026,"不能为空")
    ,NOT_EXISTENCE_COVER(10027,"文章描述未包含封面图片")
    ,NOT_EXISTENCE_ARTICLE(10028,"该文章不存在")
    ,SYSTEM_ERROR(20000,"系统异常，请从控制台或日志中查看具体错误信息");
    /**
     * 异常码
     */
    Integer code;

    /**
     * 异常信息
     */
    String msg;

    RecruitExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
