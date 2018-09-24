package com.wanjun.mail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class MailService {

    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    private JavaMailSender mailSender;
    @Resource
    private TemplateEngine templateEngine;

    public void sendSimpleMail(String to, String subject, String content){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        message.setFrom(from);

        mailSender.send(message);

    }

    public void sendHtmlMail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        helper.setFrom(from);
        mailSender.send(message);
    }

    public void sendAttachmentMail(String to, String subject, String content, String filePath) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        helper.setFrom(from);

        FileSystemResource file = new FileSystemResource(new File(filePath));
        String fileName = file.getFilename();
        helper.addAttachment(fileName, file);
        mailSender.send(message);
    }

    public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        helper.setFrom(from);
        FileSystemResource file = new FileSystemResource(new File(rscPath));
        helper.addInline(rscId, file);
        mailSender.send(message);
    }

    /**
     * 发送一个模板邮件
     */
    public void sendTemplateMail(String to, String subject, String content) throws MessagingException {
        Context context = new Context();
        // 设置邮件内容
        context.setVariable("content",content);
        // 设置签名
        context.setVariable("tel","15102154475");
        context.setVariable("email","wanjun94@qq.com");
        context.setVariable("github","JongWann");
        context.setVariable("blog","jongwann.github.io");
        String emailContent = templateEngine.process("emailTemplate", context);
        this.sendHtmlMail(to, subject, emailContent);
    }
}
