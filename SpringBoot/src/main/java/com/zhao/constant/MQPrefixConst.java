package com.zhao.constant;

import org.springframework.stereotype.Component;

/**
 * mq常量
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
@Component
public class MQPrefixConst {

    /**
     * maxwell交换机
     */
    public static final String FANOUT_MAXWELL_EXCHANGE = "maxwell.fanout.exchange";

    /**
     * email交换机
     */
    public static final String FANOUT_EMAIL_EXCHANGE = "email.fanout.exchange";

    /**
     * maxwell队列
     */
    public static final String FANOUT_MAXWELL_QUEUE = "maxwell_fanout_queue";

    /**
     * 电子邮件麦克斯韦队列
     */
    public static final String FANOUT_EMAIL_QUEUE = "email_fanout_queue";

}
