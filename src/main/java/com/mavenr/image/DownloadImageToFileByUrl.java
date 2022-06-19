package com.mavenr.image;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author mavenr
 * @Classname DownloadImageToFileByUrl
 * @Description TODO
 * @Date 2022/6/19 16:06
 */
public class DownloadImageToFileByUrl {

    /**
     * 根据 url 下载图片到本地文件
     * @param url
     * @param filePath
     */
    public static void getDownloadToFile(String url, String filePath, String fileType) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().method("GET", null).url(url)
                .addHeader("Content-Type", fileType).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            // 将流写入到文件
            byte[] bytes = response.body().bytes();
            FileOutputStream os = new FileOutputStream(new File(filePath));

            // 使用流直接写出字节数组
            os.write(bytes, 0, bytes.length);
            os.flush();
            os.close();

//				// 使用nio方式写出数据
//				FileChannel channel = os.getChannel();
//				ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
//				buffer.put(bytes);
//				// 将指针放到缓冲区的起始位置
//				buffer.flip();
//				// 判断缓冲区中是否还有数据
//				while (buffer.hasRemaining()) {
//					channel.write(buffer);
//				}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
