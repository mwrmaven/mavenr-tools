package com.mavenr.utils;

import java.util.UUID;

/**
 * @author mavenr
 * @Classname UUIDUtil
 * @Description 生成uuid的工具类
 * @Date 2022/7/14 11:25
 */
public class UUIDUtil {

    public static String getLowUUID() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-", "").toLowerCase();
        return uuid;
    }

    public static String getUpperUUID() {
        String uuid = getLowUUID();
        return uuid.toUpperCase();
    }

}
