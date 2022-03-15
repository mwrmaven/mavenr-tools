package com.mavenr.encrypt;

import com.mavenr.common.EncryptCommon;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author mavenr
 * @Classname SHA 不可逆
 * @Description SHA加解密算法
 * @Date 2021/7/7 15:59
 */
public class SHA {

    /**
     * 加密算法
     * @param key
     * @return
     */
    public static String encode(String key) {
        StringBuilder sb = new StringBuilder();
        String tmp;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(EncryptCommon.SHA_1);
            messageDigest.update(key.getBytes());
            byte[] digest = messageDigest.digest();
            for (int i = 0; i < digest.length; i++) {
                tmp = Integer.toHexString(digest[i] & 0xFF);
                if (tmp.length() == 1) {
                    sb.append("0");
                }
                sb.append(tmp);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
