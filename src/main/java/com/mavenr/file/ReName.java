package com.mavenr.file;

import com.mavenr.utils.FileUtil;

import java.io.File;

/**
 * @author mavenr
 * @Classname ReName
 * @Description modify file's name
 * @Date 2021/6/17 11:04
 */
public class ReName {

    /**
     * add identity after file name
     * @param path the path, include file and folder
     * @param endIdentification the identifier appended after the file name
     * @return
     */
    public static boolean endAppend(String path, String endIdentification) {
        File rootFile = new File(path);
        boolean flag = true;
        if (rootFile.isDirectory()) {
            // get files in folder
            File[] files = rootFile.listFiles();
            for (File f : files) {
                boolean result = renameEnd(f, endIdentification);
                if (!result) {
                    flag = false;
                }
            }
        } else if (rootFile.isFile()) {
            boolean result = renameEnd(rootFile, endIdentification);
            flag = result;
        }
        return flag;
    }

    /**
     * add identify before file name
     * @param path the path, include file and folder
     * @param beginIdentification the identifier appended before the file name
     * @return
     */
    public static boolean beginAppend(String path, String beginIdentification) {
        File rootFile = new File(path);
        boolean flag = true;
        if (rootFile.isDirectory()) {
            // get files in folder
            File[] files = rootFile.listFiles();
            for (File f : files) {
                boolean result = renameBegin(f, beginIdentification);
                if (!result) {
                    flag = false;
                }
            }
        } else if (rootFile.isFile()) {
            boolean result = renameBegin(rootFile, beginIdentification);
            flag = result;
        }
        return flag;
    }

    /**
     * replace the old characters in the file name with the new characters
     * @param path file path
     * @param oldChar old characters in file name
     * @param newChar new characters
     * @return
     */
    public static boolean replaceFileName(String path, String oldChar, String newChar) {
        File rootFile = new File(path);
        boolean flag = true;
        if (rootFile.isDirectory()) {
            File[] files = rootFile.listFiles();
            for (File f : files) {
                boolean result = rename(f, oldChar, newChar);
                if (!result) {
                    flag = false;
                }
            }
        } else if (rootFile.isFile()) {
            boolean result = rename(rootFile, oldChar, newChar);
            flag = result;
        }
        return flag;
    }

    /**
     * replace the fixed characters, and new characters include identifications and numbers
     * @param path file path
     * @param fixOldChar old characters in file name
     * @param identification logos in new characters
     * @param start begin number
     * @param step number of steps to increase the number
     * @return
     */
    public static boolean replaceFixAndIdentificationIncreaseNumber(String path, String fixOldChar, String identification,
                                                                    int start, int step) {
        // get file array
        File[] files = FileUtil.getFiles(path);
        int tmp = start;
        boolean flag = false;
        for (int i = 0; i < files.length; i++) {
            // newCharacters
            String newChar = identification + tmp;
            // replace old characters with new ones
            flag = rename(files[i], fixOldChar, newChar);
            if (!flag) {
                return flag;
            }
            tmp += step;
        }
        return flag;
    }
    /**
     * file rename
     * @param f file
     * @param endIdentification the identifier appended after the file name
     * @return
     */
    private static boolean renameEnd(File f, String endIdentification) {
        // fileName or filePath include extension
        String filePath = f.getPath();
        StringBuilder newFilePath = new StringBuilder();
        if (filePath.lastIndexOf(".") == -1) {
            newFilePath = newFilePath.append(filePath).append(endIdentification);
        } else {
            newFilePath = newFilePath.append(filePath.substring(0, filePath.lastIndexOf(".")))
                    .append(endIdentification)
                    .append(filePath.substring(filePath.lastIndexOf(".")));
        }
        boolean result = f.renameTo(new File(newFilePath.toString()));
        return result;
    }

    /**
     * file rename
     * @param f file
     * @param beginIdentification the identifier appended before the file name
     * @return
     */
    private static boolean renameBegin(File f, String beginIdentification) {
        // fileName or filePath include extension
        String filePath = f.getPath();
        StringBuilder newFilePath = new StringBuilder();
        newFilePath = newFilePath.append(filePath.substring(0, filePath.lastIndexOf(File.separator) + 1))
                .append(beginIdentification)
                .append(filePath.substring(filePath.lastIndexOf(File.separator) + 1));
        boolean result = f.renameTo(new File(newFilePath.toString()));
        return result;
    }

    /**
     * file rename:q
     * @param f file
     * @param oldChar old characters in file name
     * @param newChar new characters
     * @return
     */
    private static boolean rename(File f, String oldChar, String newChar) {
        String fileName = f.getName();
        if (!fileName.contains(oldChar)) {
            return true;
        }
        String newFileName = fileName.replaceAll(oldChar, newChar);
        String filePath = f.getPath().substring(0, f.getPath().lastIndexOf(File.separator) + 1) + newFileName;
        boolean result = f.renameTo(new File(filePath));
        return result;
    }


}
