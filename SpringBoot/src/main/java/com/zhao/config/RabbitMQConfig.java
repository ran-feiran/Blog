package com.zhao.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.zhao.constant.MQPrefixConst.*;

/**
 * rabbitmq配置类
 *
 * @author ran-feiran
 * @date 2022/09/24
 */
@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue articleQueue() {
        return new Queue(FANOUT_MAXWELL_QUEUE,true,false,false);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(FANOUT_EMAIL_QUEUE,true,false,false);
    }

    @Bean
    public FanoutExchange maxWellExchange() {
        return new FanoutExchange(FANOUT_MAXWELL_EXCHANGE, true, false);
    }

    @Bean
    public FanoutExchange emailExchange() {
        return new FanoutExchange(FANOUT_EMAIL_EXCHANGE, true, false);
    }

    @Bean
    public Binding bindingArticleFanout() {
        return BindingBuilder.bind(articleQueue()).to(maxWellExchange());
    }

    @Bean
    public Binding bindingEmailFanout() {
        return BindingBuilder.bind(emailQueue()).to(emailExchange());
    }
}
