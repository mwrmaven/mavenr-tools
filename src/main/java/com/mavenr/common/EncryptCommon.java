package com.mavenr.common;

/**
 * @author mavenr
 * @Classname EncryptCommon
 * @Description TODO
 * @Date 2022/3/15 11:04
 */
public class EncryptCommon {
    /**
     * 编码格式
     */
    public static final String ENCODING = "UTF-8";

    /**
     * 加密方式
     */
    public static final String MD5 = "MD5";
    public static final String SHA_1 = "SHA-1";
    public static final String SM4 = "SM4";

    /**
     * 加密算法/分组加密模式/分组填充模式
     * PKCS5Padding 以8个字节为一组进行分组加密
     */
    public static final String SM4_ECB_PKCS5 = "SM4/ECB/PKCS5Padding";
}
