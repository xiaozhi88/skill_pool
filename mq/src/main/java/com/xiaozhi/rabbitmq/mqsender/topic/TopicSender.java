package com.xiaozhi.rabbitmq.mqsender.topic;

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
public class TopicSender {

    @Resource
    RabbitTemplate rabbitTemplate;


    public void send(int i) {
        rabbitTemplate.convertAndSend(RabbitMQConstant.EXCHANGE_TOPIC, RabbitMQConstant.ROUTING_KEY_TOPIC_FIRST, i, new CorrelationData(UUID.randomUUID().toString().replaceAll("-", "")));
//        rabbitTemplate.convertAndSend(RabbitMQConstant.EXCHANGE_TOPIC, RabbitMQConstant.ROUTING_KEY_TOPIC_SECOND, i, new CorrelationData(UUID.randomUUID().toString().replaceAll("-", "")));
    }


}
