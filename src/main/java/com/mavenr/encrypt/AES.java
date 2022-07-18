package com.mavenr.encrypt;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author mavenr
 * @Classname AES 加密
 * @Description TODO
 * @Date 2022/7/18 14:42
 */
public class AES {

    /**
     * 使用 AES-128-CBC加密模式，key需要为16位
     * @param key
     * @param iv
     * @param text
     * @return
     * @throws Exception
     */
    public static String encrypt_CBC_NOPADDING(String key, String iv, String text) throws Exception {
        // 创建一个加密实例 算法/模式/补码方式
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        // 获取空位大小
        int blockSize = cipher.getBlockSize();
        // 获取要加密的数据的字节数组
        byte[] dataBytes = text.getBytes();
        // 获取字节数组的长度
        int textByteLength = dataBytes.length;
        // 补全空位
        if (textByteLength % blockSize != 0) {
            textByteLength = textByteLength + (blockSize - (textByteLength % blockSize));
        }

        byte[] newText = new byte[textByteLength];
        System.arraycopy(dataBytes, 0, newText, 0, dataBytes.length);

        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());

        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] encrypted = cipher.doFinal(newText);

        return new Base64().encodeToString(encrypted);
    }

}
