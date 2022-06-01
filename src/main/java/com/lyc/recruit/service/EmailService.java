package com.lyc.recruit.service;

/**
 * 邮件Service
 */
public interface EmailService {

    void sendSimpleMessage(String to, String subject, String text);

    Boolean saveEmailToRedis(String email, String verificationCode);

    Boolean checkEmailAndCode(String email, String verificationCode);
}
