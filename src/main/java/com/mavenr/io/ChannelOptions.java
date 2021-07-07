package com.mavenr.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * @author mavenr
 * @Classname ChannelOptions
 * @Description 流操作
 * @Date 2021/7/7 16:38
 */
public class ChannelOptions {

    /**
     * 将输入流信息写入到输出流
     * @param inputStream
     * @param outputStream
     * @param capacity
     * @throws IOException
     */
    private static void fromInputToOut(InputStream inputStream, OutputStream outputStream, int capacity) throws IOException {
        // 输入通道
        ReadableByteChannel readableByteChannel = Channels.newChannel(inputStream);
        // 输出通道
        WritableByteChannel writableByteChannel = Channels.newChannel(outputStream);
        // 缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);

        // 从输入通道拷贝到输出通道
        while (readableByteChannel.read(byteBuffer) != -1) {
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                writableByteChannel.write(byteBuffer);
            }
            byteBuffer.clear();
        }

        // 关闭通道
        writableByteChannel.close();
        readableByteChannel.close();
    }

    /**
     * 输入流写入到输出流，缓存区 100KB
     * @param inputStream
     * @param outputStream
     * @throws IOException
     */
    public static void fromInputToOut(InputStream inputStream, OutputStream outputStream) throws IOException {
        fromInputToOut(inputStream, outputStream, 100 * 1024);
    }
}
