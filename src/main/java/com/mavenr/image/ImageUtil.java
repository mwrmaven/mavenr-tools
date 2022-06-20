package com.mavenr.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author mavenr
 * @Classname ImageUtil
 * @Description 图片工具类
 * @Date 2022/6/20 15:38
 */
public class ImageUtil {

    /**
     * 合并图片
     * @param imgPaths
     * @param imgType
     * @param outImgPath
     * @return
     */
    public static boolean mergeUpAndDown(String[] imgPaths, String imgType, String outImgPath) {
        // 总高和总宽
        int dstHeight = 0;
        int dstWidth = 0;
        // 图片个数
        int size = imgPaths.length;
        if (size < 2) {
            return true;
        }

        File[] file = new File[size];
        BufferedImage[] images = new BufferedImage[size];

        int[][] imageArrays = new int[size][];

        for (int i = 0; i < size; i++) {
            file[i] = new File(imgPaths[i]);
            try {
                images[i] = ImageIO.read(file[i]);
            } catch (IOException e) {
                System.out.println("读取" + imgPaths[i] + "图片失败");
                e.printStackTrace();
            }

            int width = images[i].getWidth();
            int height = images[i].getHeight();

            // 从图片中读取rgb像素
            imageArrays[i] = new int[width * height];
            images[i].getRGB(0, 0, width, height, imageArrays[i], 0, width);

            // 计算合并后的宽度和高度
            dstWidth = Math.max(dstWidth, width);
            dstHeight += height;
        }

        // 合成图片的像素大小
        System.out.println("合成宽度：" + dstWidth);
        System.out.println("合成高度：" + dstHeight);

        if (dstHeight < 1) {
            System.out.println("合成图片的高度小于1");
            return false;
        }

        // 生成新图片
        BufferedImage imageDest = new BufferedImage(dstWidth, dstHeight, BufferedImage.TYPE_INT_RGB);
        int h = 0;
        for (int i = 0; i < size; i++) {
            int width = images[i].getWidth();
            int height = images[i].getHeight();
            imageDest.setRGB(0, h, width, height, imageArrays[i], 0, width);
            h += height;
        }

        File outFile = new File(outImgPath);
        // 写图片到硬盘
        try {
            ImageIO.write(imageDest, imgType, outFile);
        } catch (IOException e) {
            System.out.println("合并图片出错");
            e.printStackTrace();
        }
        return true;
    }
}
