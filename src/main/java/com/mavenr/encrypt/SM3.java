package com.mavenr.encrypt;

import com.mavenr.common.EncryptCommon;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import java.io.UnsupportedEncodingException;

/**
 * @author mavenr
 * @Classname SM3 杂凑算法 不可逆
 * @Description SM3是GB/T 32905-2016信息安全技术SM3密码杂凑算法中定义的密码杂凑算法
 * 对长度为l(l < 2的64次方) 比特的消息m， SM3杂凑算法经过填充和迭代压缩，生成杂凑值，杂凑值长度为256比特
 * @Date 2021/10/19 10:21
 */
public class SM3 {

    /**
     * sm3加密(无密钥)
     * @param text 源字符串
     * @return
     */
    public static String encode(String text) {
        String result = "";
        try {
            // 将字符串转换为数组
            byte[] bytes = text.getBytes(EncryptCommon.ENCODING);
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
     * sm3加密（有密钥）
     * @param key 密钥
     * @param text 源字符串
     * @return
     */
    public static String encodeByKey(String key, String text) {
        String result = "";
        try {
            // 将字符串转换为数组
            byte[] bytes = text.getBytes(EncryptCommon.ENCODING);
            byte[] keyBytes = key.getBytes(EncryptCommon.ENCODING);
            byte[] hash = hkey(keyBytes, bytes);
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

    /**
     * 通过密钥进行加密
     * @param key 密钥
     * @param srcData 源数据
     * @return
     */
    private static byte[] hkey(byte[] key, byte[] srcData) {
        KeyParameter keyParameter = new KeyParameter(key);
        SM3Digest digest = new SM3Digest();
        HMac mac = new HMac(digest);
        mac.init(keyParameter);
        mac.update(srcData, 0, srcData.length);
        byte[] hash = new byte[mac.getMacSize()];
        mac.doFinal(hash, 0);
        return hash;
    }

}
