package com.zhao.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailMapperImpl  {

    @Autowired
    JavaMailSender javaMailSender;

    public String sendEmail(String email) {
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(9) + 1);
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("2729627149@qq.com");
        message.setTo(email);
        message.setSubject("验证码");
        message.setText("您的验证码为: " + code + "，有效期15分钟，请不要告诉他人哦！");
        javaMailSender.send(message);
        return code.toString();
    }
}
