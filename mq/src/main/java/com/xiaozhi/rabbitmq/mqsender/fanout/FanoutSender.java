package com.xiaozhi.rabbitmq.mqsender.fanout;

import com.xiaozhi.rabbitmq.constant.RabbitMQConstant;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @Description:
 * @Author: qjc
 * @Date: 2019/12/6
 */
@Component
public class FanoutSender {

    @Resource
    RabbitTemplate rabbitTemplate;


    public void send(int i) {
        rabbitTemplate.convertAndSend(RabbitMQConstant.EXCHANGE_FANOUT, RabbitMQConstant.ROUTING_KEY_TOPIC_FIRST, i, new CorrelationData(UUID.randomUUID().toString().replaceAll("-", "")));
    }


}
