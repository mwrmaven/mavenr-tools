package com.mavenr.file;

import com.mavenr.enums.SortType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author mavenr
 * @Classname FileContentSort
 * @Description 文件内容排序，并输出到新文件
 *              sort the contents of the file and output to the new file
 * @Date 2021/10/8 9:07
 */
public class FileContentSort {
    /**
     * 以文本行中的分隔符排序
     * sort by separator in text line of the file
     * @param files the source files
     * @param splitStr separator in text line
     * @param num index of text which is separated
     * @param sortType sort type ( 1 is asc, -1 is desc, the default is ascending)
     */
    public static void sortText(List<File> files, String splitStr, int num, int sortType) {
        for (File f : files) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(f));
                String line;
                Map<String, List<String>> map = new HashMap<>();
                while ((line = br.readLine()) != null) {
                    if (map.get(line.split(splitStr)[num - 1]) == null) {
                        List<String> ls = new ArrayList<>();
                        ls.add(line);
                        map.put(line.split(splitStr)[num - 1], ls);
                    } else {
                        List<String> ls = map.get(line.split(splitStr)[num - 1]);
                        ls.add(line);
                    }

                }

                List<String> keys = map.keySet().stream().sorted(new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        if (SortType.ASC.getCode() == sortType) {
                            return o1.compareTo(o2);
                        } else if (SortType.DESC.getCode() == sortType) {
                            return o2.compareTo(o1);
                        } else {
                            return o1.compareTo(o2);
                        }

                    }
                }).collect(Collectors.toList());
                StringBuilder sb = new StringBuilder();
                for (String k : keys) {
                    List<String> strings = map.get(k);
                    for (String text : strings) {
                        sb.append(text).append("\n");
                    }
                }
                String path = f.getPath();
                File newFile = new File(path.substring(0, path.lastIndexOf(".")) + "_new" + path.substring(path.lastIndexOf(".")));
                newFile.createNewFile();
                FileChannel fc = new FileOutputStream(newFile).getChannel();
                ByteBuffer buffer = ByteBuffer.allocate(40960);
                buffer.put(sb.toString().getBytes());
                buffer.flip();
                fc.write(buffer);
                fc.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 以文本行中字符的范围区间排序
     * sort by the range of characters in the text line
     * @param files the source files
     * @param start start index
     * @param end end index
     * @param sortType sort type ( 1 is asc, -1 is desc, the default is ascending)
     */
    public static void sortText(List<File> files, int start, int end, int sortType) {
        for (File f : files) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(f));
                String line;
                Map<String, List<String>> map = new HashMap<>();
                while ((line = br.readLine()) != null) {
                    if (start < 0) {
                        start = 0;
                    }
                    if (end < start) {
                        end = start;
                    }
                    if (end >= line.length()) {
                        end = line.length();
                    }

                    if (map.get(line.substring(start - 1, end)) == null) {
                        List<String> ls = new ArrayList<>();
                        ls.add(line);
                        map.put(line.substring(start - 1, end), ls);
                    } else {
                        List<String> ls = map.get(line.substring(start - 1, end));
                        ls.add(line);
                    }
                }

                List<String> keys = map.keySet().stream().sorted(new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        if (SortType.ASC.getCode() == sortType) {
                            return o1.compareTo(o2);
                        } else if (SortType.DESC.getCode() == sortType) {
                            return o2.compareTo(o1);
                        } else {
                            return o1.compareTo(o2);
                        }
                    }
                }).collect(Collectors.toList());
                StringBuilder sb = new StringBuilder();
                for (String k : keys) {
                    List<String> strings = map.get(k);
                    for (String text : strings) {
                        sb.append(text).append("\n");
                    }
                }
                String path = f.getPath();
                File newFile = new File(path.substring(0, path.lastIndexOf(".")) + "_new" + path.substring(path.lastIndexOf(".")));
                newFile.createNewFile();
                FileChannel fc = new FileOutputStream(newFile).getChannel();
                ByteBuffer buffer = ByteBuffer.allocate(40960);
                buffer.put(sb.toString().getBytes());
                buffer.flip();
                fc.write(buffer);
                fc.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
