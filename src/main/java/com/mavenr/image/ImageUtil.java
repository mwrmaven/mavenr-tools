package com.mavenr.image;

import com.mavenr.bo.ImageWidthAndHeightt;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    public static boolean mergeUpAndDown(List<String> imgPaths , String imgType, String outImgPath) {
        // 图片个数
        int size = imgPaths.size();
        if (size < 2) {
            return true;
        }

        // 总高和总宽
        int dstHeight = 0;
        int dstWidth = 0;

        File[] file = new File[size];
        BufferedImage[] images = new BufferedImage[size];

        for (int i = 0; i < size; i++) {
            String imgPath = imgPaths.get(i);
            file[i] = new File(imgPath);
            try {
                images[i] = ImageIO.read(file[i]);
            } catch (IOException e) {
                System.out.println("读取" + imgPath + "图片失败");
                e.printStackTrace();
            }

            int width = images[i].getWidth();
            // 计算合并后的最大宽度
            dstWidth = Math.max(dstWidth, width);
        }

        int[][] imageArrays = new int[size][];
        // 将较窄的图片放大到最大宽
        List<ImageWidthAndHeightt> whList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int width = images[i].getWidth();
            int height = images[i].getHeight();
            if (width == dstWidth) {
                // 从图片中读取rgb像素
                imageArrays[i] = new int[width * height];
                images[i].getRGB(0, 0, width, height, imageArrays[i], 0, width);
                dstHeight += height;
                continue;
            }
            // 放大比例
            double rate = (double) dstWidth / width;
            images[i] = scaleImage(images[i], rate);

            width = images[i].getWidth();
            height = images[i].getHeight();
            // 从图片中读取rgb像素
            imageArrays[i] = new int[width * height];
            images[i].getRGB(0, 0, width, height, imageArrays[i], 0, width);
            dstHeight += height;

            // 记录图片的宽、高
            ImageWidthAndHeightt iwah = new ImageWidthAndHeightt();
            iwah.setWidth(width);
            iwah.setHeight(height);
            whList.add(iwah);
            // 释放读取图片的BufferedImage
            images[i].getGraphics().dispose();
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
            ImageWidthAndHeightt wh = whList.get(i);
            int width = wh.getWidth();
            int height = wh.getHeight();
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
        } finally {
            // 释放读取图片的BufferedImage
            imageDest.getGraphics().dispose();
        }
        return true;
    }

    /**
     * 缩放图片
     * @return
     */
    public static BufferedImage scaleImage(BufferedImage image, double rate) {
        // 获取图片的原始宽高
        int width = image.getWidth();
        int height = image.getHeight();
        // 获取图片的缩放（宽高在乘以比例之后再取整）
        Image scaleInstance = image.getScaledInstance(Double.valueOf(width * rate).intValue(),
                Double.valueOf(height * rate).intValue(), Image.SCALE_DEFAULT);

        // 缩放后的新图
        BufferedImage outImage = new BufferedImage(Double.valueOf(width * rate).intValue(),
                Double.valueOf(height * rate).intValue(), BufferedImage.TYPE_INT_RGB);
        Graphics g = outImage.getGraphics();
        g.drawImage(scaleInstance, 0, 0, null);
        g.dispose();
        System.out.println("缩放图片成功！");
        return outImage;
    }
}
