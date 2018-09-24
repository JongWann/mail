package com.wanjun.mail.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {

    @Resource
    private MailService mailService;

    @Resource
    private TemplateEngine templateEngine;

    /**
     * 测试发送普通的文本邮件
     */
    @Test
    public void sendSimpleMessageTest(){
        mailService.sendSimpleMail("wanjun94@qq.com","test","test");
    }

    /**
     * 测试发送html邮件
     * @throws MessagingException
     */
    @Test
    public void sendHtmlTest() throws MessagingException {
        String content = "<html>" +
                "<body>" +
                "<h3>这是一封HTML邮件</h3>"+
                "</body>"+
                "</html>";
        mailService.sendHtmlMail("wanjun94@qq.com", "html mail", content);
    }

    /**
     * 测试发送附件
     * @throws MessagingException
     */
    @Test
    public void sendAttachmentMailTest() throws MessagingException {
        String filepath = "/Users/junwan/myapp/测试.txt";
        mailService.sendAttachmentMail("wanjun94@qq.com", "attachment", "附件", filepath);
    }

    /**
     * 测试发送图片邮件
     * @throws MessagingException
     */
    @Test
    public void sendInlineResourceTest() throws MessagingException {
        String path = "/Users/junwan/Downloads/0.jpeg";
        String id = "image001";
        String content = "<html><body>这是有图片的邮件<img src=\'cid:"+id+ "\'/>"+
                "</body></html>";
        mailService.sendInlineResourceMail("wanjun94@qq.com","inline", content,
                path, id);
    }

    /**
     * 测试使用邮件模板发送邮件
     * @throws MessagingException
     */
    @Test
    public void testTemplateMailTest() throws MessagingException {
        Context context = new Context();
        context.setVariable("content","你今天学习了吗");
        context.setVariable("tel","15102154475");
        context.setVariable("email","wanjun94@qq.com");
        context.setVariable("github","JongWann");
        context.setVariable("blog","jongwann.github.io");
        String emailContent = templateEngine.process("emailTemplate", context);

        mailService.sendHtmlMail("wanjun94@qq.com", "这是一个模板邮件", emailContent);
    }
}
