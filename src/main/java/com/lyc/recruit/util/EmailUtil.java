package com.lyc.recruit.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * 描述：     Email工具
 */
public class EmailUtil {

    /**
     * 生成随机验证码
     * @return
     */
    public static String getVerificationCode() {
        List<String> verificationChars = Arrays.asList(
                new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
                        "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"});
        //对List集合中的元素随机打散
        Collections.shuffle(verificationChars);
        String result = "";
        for (int i = 0; i < 6; i++) {
            result += verificationChars.get(i);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(EmailUtil.getVerificationCode());
    }

    /**
     * 校验邮箱是否有效合法
     * @param email
     * @return 有效返回true，无效返回false
     */
    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
        } catch (AddressException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

}
