package com.mavenr.wechat;

import com.mavenr.encrypt.SHA;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * @author mavenr
 * @Classname Verify
 * @Description 微信接口验证
 * @Date 2021/7/7 15:49
 */
public class Verify {

    /**
     * 验证微信代码
     * @param signature 微信加密签名
     * @param randomStr 随机字符串
     * @param timestamp 时间戳
     * @param randomNum 随机数
     * @param token 关键字（在微信公众平台开发管理中定义）
     * @return
     */
    public static String wxCodeVerify(String signature, String randomStr,
                                      String timestamp, String randomNum, String token) {
        String[] str = {token, timestamp, randomNum};
        Arrays.sort(str);
        String bigStr = str[0] + str[1] + str[2];

        // SHA-1加密
        String digest = SHA.encode(bigStr);

        // 验证签名是否合法，合法，则返回随机字符串
        if (digest.equalsIgnoreCase(signature)) {
            return randomStr;
        } else {
            return  randomStr + "-false";
        }
    }
}
