package com.mavenr.encrypt;

import com.mavenr.common.EncryptCommon;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

/**
 * @author mavenr
 * @Classname SM4 可逆算法
 * @Description sm4国密加解密
 * @Date 2022/3/15 10:40
 */
public class SM4 {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 生成 ecb 暗号
     * @param algorithmName 算法名称
     * @param mode 模式
     * @param key 密钥
     * @return ecb
     */
    private static Cipher generateEcbCipher(String algorithmName, int mode, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithmName, BouncyCastleProvider.PROVIDER_NAME);
        Key sm4Key = new SecretKeySpec(key, EncryptCommon.SM4);
        cipher.init(mode, sm4Key);
        return cipher;
    }

    /**
     * 系统生成密钥
     * @return 加密后的信息
     */
    public static String generateKey() throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance(EncryptCommon.SM4, BouncyCastleProvider.PROVIDER_NAME);
        kg.init(128, new SecureRandom());
        byte[] encoded = kg.generateKey().getEncoded();
        return ByteUtils.toHexString(encoded);
    }

    /**
     * 加密（使用自己提供的十六进制的密钥）
     * @param hexKey 密钥
     * @param text 加密前的信息
     * @return 加密后的信息
     */
    public static String encodeByKey(String hexKey, String text) throws Exception {
        byte[] keyData = ByteUtils.fromHexString(hexKey);
        byte[] srcData = text.getBytes(EncryptCommon.ENCODING);
        // 加密后的数组
        byte[] encoded = encrypt_ecb_padding(keyData, srcData);
        return ByteUtils.toHexString(encoded);
    }

    /**
     * 加密
     * @param key 密钥
     * @param srcData 源信息
     * @return 加密后的信息
     * @throws Exception
     */
    private static byte[] encrypt_ecb_padding(byte[] key, byte[] srcData) throws Exception {
        Cipher cipher = generateEcbCipher(EncryptCommon.SM4_ECB_PKCS5, Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(srcData);
    }

    /**
     * 解密（使用自己提供的十六进制的密钥）
     * @param hexKey 密钥
     * @param secretWord 加密后的信息
     * @return 加密前的信息
     */
    public static String decodeByKey(String hexKey, String secretWord) throws Exception {
        byte[] keyData = ByteUtils.fromHexString(hexKey);
        // 将加密信息转换为十六进制byte数组
        byte[] cipherData = ByteUtils.fromHexString(secretWord);
        // 解密
        byte[] srcData = decrypt_ecb_padding(keyData, cipherData);
        // 返回加密前的信息
        return new String(srcData, EncryptCommon.ENCODING);
    }

    /**
     * 解密
     * @param key 密钥
     * @param cipherData 加密前的信息
     * @return 加密后的信息
     * @throws Exception
     */
    private static byte[] decrypt_ecb_padding(byte[] key, byte[] cipherData) throws Exception {
        Cipher cipher = generateEcbCipher(EncryptCommon.SM4_ECB_PKCS5, Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(cipherData);
    }
}
