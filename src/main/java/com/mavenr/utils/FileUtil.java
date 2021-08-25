package com.mavenr.utils;

import java.io.File;

/**
 * @author mavenr
 * @Classname FileUtil
 * @Description file tool class
 * @Date 2021/8/25 15:07
 */
public class FileUtil {

    /**
     * get file collection based on folder or file path
     * @param path folder path or file path
     * @return
     */
    public static File[] getFiles(String path) {
        File file = new File(path);
        File[] result = null;
        if (file.isFile()) {
            result = new File[1];
            result[0] = file;
        } else if (file.isDirectory()) {
            result = file.listFiles();
        }
        return result;
    }
}
