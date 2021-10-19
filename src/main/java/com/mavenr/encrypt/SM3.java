package com.mavenr.encrypt;

import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import java.io.UnsupportedEncodingException;

/**
 * @author mavenr
 * @Classname SM3 杂凑算法
 * @Description SM3是GB/T 32905-2016信息安全技术SM3密码杂凑算法中定义的密码杂凑算法
 * 对长度为l(l < 2的64次方) 比特的消息m， SM3杂凑算法经过填充和迭代压缩，生成杂凑值，杂凑值长度为256比特
 * @Date 2021/10/19 10:21
 */
public class SM3 {

    /**
     * sm3加密
     * @param text 源字符串
     * @return
     */
    public static String encode(String text) {
        String result = "";
        try {
            // 将字符串转换为数组
            byte[] bytes = text.getBytes("UTF-8");
            byte[] hash = hash(bytes);
            // 将返回的hash值转换成16进制的字符串
            result = ByteUtils.toHexString(hash);
            // 调用hash
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @param srcData
     * @return
     */
    private static byte[] hash(byte[] srcData) {
        SM3Digest digest = new SM3Digest();
        digest.update(srcData, 0, srcData.length);
        byte[] hash = new byte[digest.getDigestSize()];
        digest.doFinal(hash, 0);
        return hash;
    }

}
