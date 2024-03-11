package com.community;

import com.community.util.MailClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MailTests {

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;  //这里爆红是IDEA的问题，不是代码问题

    //测试发送文本邮件
    @Test
    public void testTextMail(){
        mailClient.sendMail("1228145722@qq.com","test","hello!");
    }

    @Test
    public void testHtmlMail(){
        Context context = new Context();
        context.setVariable("username","MYC");
        String content = templateEngine.process("/mail/demo",context);
        System.out.println(content);
        mailClient.sendMail("1228145722@qq.com","HTML",content);
    }

}
