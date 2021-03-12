package com.xiaozhi.rabbitmq.mqsender.work;

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
public class WorkSender {

    @Resource
    RabbitTemplate rabbitTemplate;


    public void send(int i) {
        rabbitTemplate.convertAndSend(RabbitMQConstant.EXCHANGE_WORK, RabbitMQConstant.ROUTING_KEY_WORK, i, new CorrelationData(UUID.randomUUID().toString().replaceAll("-", "")));
    }


}
