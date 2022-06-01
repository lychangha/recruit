package com.lyc.recruit.service.impl;

import com.lyc.recruit.common.Constant;
import com.lyc.recruit.service.EmailService;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * EmailService实现类
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * 发送邮件
     *
     * @param to      发到那个邮箱
     * @param subject 主题
     * @param text    发送的内容
     */
    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(Constant.EMAIL_FROM);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        mailSender.send(simpleMailMessage);
    }

    /**
     * 将验证码暂时保存到redis中
     *
     * @param email            邮箱
     * @param verificationCode 验证码
     * @return
     */
    @Override
    public Boolean saveEmailToRedis(String email, String verificationCode) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.26.129:6379");
        config.useSingleServer().setPassword("123456");
        RedissonClient client = Redisson.create(config);
        RBucket<String> bucket = client.getBucket(email);
        boolean exists = bucket.isExists();
        if (!exists) {
            bucket.set(verificationCode, 60, TimeUnit.SECONDS);
            return true;
        }
        //说明用户已经获取到验证码了，想在60秒内再次获取，返回false
        return false;
    }

    /**
     * 验证redis中的验证码和用户传过来的是否一直
     * @param email 获取redis中存储的value对应的key
     * @param verificationCode 用户输入的验证码
     * @return
     */
    @Override
    public Boolean checkEmailAndCode(String email, String verificationCode) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.26.129:6379");
        config.useSingleServer().setPassword("123456");
        RedissonClient client = Redisson.create(config);
        //通过email这个key，获取到bucket
        RBucket<String> bucket = client.getBucket(email);
        //判断bucket中存不存在email对应的value
        boolean exists = bucket.isExists();
        if (exists) {
            String code = bucket.get();
            //redis里存储的验证码，和用户穿过来的一致，则校验通过
            if (code.equals(verificationCode)) {
                return true;
            }
        }
        return false;
    }
}
