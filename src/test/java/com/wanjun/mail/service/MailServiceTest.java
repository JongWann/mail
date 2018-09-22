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

    @Test
    public void sendSimpleMessageTest(){
        mailService.sendSimpleMail("wanjun94@qq.com","test","test");
    }

    @Test
    public void sendHtmlTest() throws MessagingException {
        String content = "<html>" +
                "<body>" +
                "<h3>这是一封HTML邮件</h3>"+
                "</body>"+
                "</html>";
        mailService.sendHtmlMail("wanjun94@qq.com", "html mail", content);
    }

    @Test
    public void sendAttachmentMailTest() throws MessagingException {
        String filepath = "/Users/junwan/myapp/测试.txt";
        mailService.sendAttachmentMail("wanjun94@qq.com", "attachment", "附件", filepath);
    }

    @Test
    public void sendInlineResourceTest() throws MessagingException {
        String path = "/Users/junwan/Downloads/0.jpeg";
        String id = "image001";
        String content = "<html><body>这是有图片的邮件<img src=\'cid:"+id+ "\'/>"+
                "</body></html>";
        mailService.sendInlineResourceMail("wanjun94@qq.com","inline", content,
                path, id);
    }

    @Test
    public void testTemplateMailTest() throws MessagingException {
        Context context = new Context();
        context.setVariable("link","www.baidu.com");

        String emailContent = templateEngine.process("emailTemplate", context);

        mailService.sendHtmlMail("wanjun94@qq.com", "这是一个模板邮件", emailContent);
    }
}
