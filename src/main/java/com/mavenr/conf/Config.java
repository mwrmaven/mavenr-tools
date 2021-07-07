package com.mavenr.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author mavenr
 * @Classname Config
 * @Description 读取配置文件，返回key、value格式
 * @Date 2021/7/7 16:35
 */
public class Config {
    private static final Properties PROPERTIES = new Properties();
    private static String CLASSPATH_URL_PREFIX = "classpath:";

    public static void load(String filePath) throws Exception{
        try {
            if (filePath.startsWith(CLASSPATH_URL_PREFIX)) {
                PROPERTIES.load(ClassLoader.getSystemResourceAsStream(filePath.substring(CLASSPATH_URL_PREFIX.length()).trim()));
            } else {
                PROPERTIES.load(new InputStreamReader(new FileInputStream(new File(filePath))));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

}
