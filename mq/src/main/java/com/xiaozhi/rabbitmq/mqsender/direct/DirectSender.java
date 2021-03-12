package com.xiaozhi.rabbitmq.mqsender.direct;

import com.xiaozhi.rabbitmq.constant.RabbitMQConstant;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
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
public class DirectSender {

    @Resource
    RabbitTemplate rabbitTemplate;


    public void send(Integer i) {
        /*
        注：虽然持久化消息可以做到消息不丢失，但持久化的消息在进入队列前会被写到磁盘，这个过程比写到内存慢得多，所以会严重的影响性能，
        可能导致消息的吞吐量降低10倍不止。所以，在做消息持久化前，一定要认真考虑性能和需求之间的平衡
         */
        String msg = "Hello Msg -->" + i;
        Message message = MessageBuilder.withBody(msg.getBytes()).build();
        // 消息持久化
        message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        message.getMessageProperties().setContentType(MessageProperties.CONTENT_TYPE_JSON);
        rabbitTemplate.convertAndSend(RabbitMQConstant.EXCHANGE_DIRECT, RabbitMQConstant.ROUTING_KEY_EASY, message, new CorrelationData(UUID.randomUUID().toString().replaceAll("-", "")));
    }


}
