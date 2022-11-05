package com.zhao.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
@Deprecated
public class EmailServiceImpl {

    @Autowired
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    String sender;

    public String sendEmail(String email) throws MessagingException {
        StringBuffer code = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(9) + 1);
        }
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(sender);
        helper.setTo(email);
        helper.setSubject("【一个简单的技术分享页】验证码");
        helper.setText("您的验证码为:  <b style='color:orange;font-size:30px;margin-left:5px;margin-right:5px'>" +
                code + "</b> ，有效期15分钟，请勿泄露他人，如非本人操作，请忽略此信息。", true);
        javaMailSender.send(mimeMessage);
        return code.toString();
    }
}
