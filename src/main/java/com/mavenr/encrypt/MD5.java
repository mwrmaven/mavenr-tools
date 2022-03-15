package com.mavenr.encrypt;

import com.mavenr.common.EncryptCommon;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author mavenr
 * @Classname MD5 不可逆
 * @Description MD5加解密算法
 * @Date 2021/7/7 16:04
 */
public class MD5 {

    /**
     * 加密
     * @param key
     * @return
     */
    public static String encode(String key) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(EncryptCommon.MD5);
            byte[] digest = messageDigest.digest(key.getBytes());
            StringBuilder tmp = new StringBuilder();
            for (int i = 0; i < digest.length; i++) {
                int bt = digest[i] & 0xFF;
                if (bt < 16) {
                    tmp.append("0");
                }
                tmp.append(Integer.toHexString(bt));
            }
            return tmp.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
