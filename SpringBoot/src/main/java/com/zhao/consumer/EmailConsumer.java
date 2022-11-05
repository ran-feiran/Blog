package com.zhao.consumer;


import com.alibaba.fastjson2.JSON;
import com.zhao.dto.EmailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static com.zhao.constant.MQPrefixConst.FANOUT_EMAIL_QUEUE;


@Component
@Slf4j
@RabbitListener(queues = FANOUT_EMAIL_QUEUE)
public class EmailConsumer {

    /**
     * 邮箱号
     */
    @Value("${spring.mail.username}")
    private String email;

    @Autowired
    private JavaMailSender javaMailSender;

    @RabbitHandler
    public void process(byte[] data) throws MessagingException {
        EmailDTO emailDTO = JSON.parseObject(new String(data), EmailDTO.class);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(email);
        helper.setTo(emailDTO.getEmail());
        helper.setSubject(emailDTO.getSubject());
        helper.setText(emailDTO.getContent());
        javaMailSender.send(mimeMessage);
    }



}

