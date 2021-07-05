package com.mavenr.email;


import javax.mail.*;
import javax.mail.internet.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @Author mavenr
 * @Classname  MavenrQQEmail
 * @Description QQ邮件实现类
 * @Date 2021/7/1 7:00 下午
 */
public class MavenrQQEmail implements MavenrEmail{

    private String username;

    private String password;

    public MavenrQQEmail(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * 发送普通邮件(多接收人)
     * @param from 发送人邮箱
     * @param to 接收人邮箱
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    @Override
    public void sendMail(String from, List<String> to, String subject, String content) {
        try {
            Message message = getMessage(from, to);
            message.setSubject(subject);
            message.setText(content);
            // 发送邮件
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    /**
     * 发送html格式内容的邮件(多接收人)
     * @param from 发送人
     * @param to 接收人集合
     * @param subject 主题
     * @param content html格式内容
     */
    public void sendHtmlMail(String from, List<String> to, String subject, String content) {
        try {
            Multipart multipart = new MimeMultipart();
            BodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(content, "text/html");
            multipart.addBodyPart(bodyPart);
            Message message = getMessage(from, to);
            message.setSubject(subject);
            message.setContent(multipart);
            message.saveChanges();
            // 发送邮件
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送普通邮件(单个接收人)
     * @param from 发送人邮箱
     * @param to 接收人邮箱
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    public void sendMail(String from, String to, String subject, String content) {
        List<String> tos = new ArrayList<>();
        tos.add(to);
        sendMail(from, tos, subject, content);
    }

    /**
     * 发送邮件内容html格式的单个接收人邮件
     * @param from 发送人
     * @param to 接收人集合
     * @param subject 主题
     * @param content html格式内容，例如 <div>test</div>
     */
    public void sendHtmlMail(String from, String to, String subject, String content) {
        List<String> tos = new ArrayList<>();
        tos.add(to);
        sendHtmlMail(from, tos, subject, content);
    }


    /**
     * 根据发送人和接收人，创建邮件消息
     * @param from
     * @param to
     * @return
     * @throws MessagingException
     */
    public Message getMessage(String from, List<String> to) throws MessagingException {
        // 设置QQ邮箱参数
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.qq.com");
        properties.put("mail.smtp.port", 465);
        properties.put("mail.smtp.ssl.enable", true);

        // 创建Session会话
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        // 逗号拼接地址
        StringBuilder addresses = new StringBuilder();
        for (String t : to) {
            addresses.append(t).append(",");
        }
        String realAddresses = addresses.substring(0, addresses.length() - 1);
        // 创建消息
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(realAddresses));

        return message;
    }
}
