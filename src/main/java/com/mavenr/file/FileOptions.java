package com.mavenr.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author mavenr
 * @Classname FileOptions
 * @Description 文件操作类
 * @Date 2022/6/22 0:02
 */
public class FileOptions {

    /**
     * 删除文件、文件夹以及文件夹中的子项
     * @param file 文件或文件夹
     */
    public static void deleteFile(File file) {
        if (!file.exists()) {
            return;
        }

        try {
            Path path = file.toPath();
            if (file.isFile()) {
                Files.deleteIfExists(path);
                return;
            }
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null && files.length != 0) {
                    for (File f : files) {
                        deleteFile(f);
                    }
                }
                Files.deleteIfExists(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
