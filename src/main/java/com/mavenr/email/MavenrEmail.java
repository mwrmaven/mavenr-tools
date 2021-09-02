package com.mavenr.email;

import java.util.List;

/**
 * @Author mavenr
 * @Classname  MavenrEmail
 * @Description 自定义邮件接口
 * @Date 2021/7/1 6:26 下午
 */
public interface MavenrEmail {

    /**
     * 发送普通邮件
     * @param from 发送人邮箱
     * @param to 接收人邮箱
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    void sendMail(String from, List<String> to, String subject, String content) throws Exception;

}
