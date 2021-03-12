package com.xiaozhi.rabbitmq.callbackconfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: qjc
 * @Date: 2019/12/10
 */
@Component
@Slf4j
public class MsgSendReturnCallback implements RabbitTemplate.ReturnCallback {

    /**
     * 使用该功能需要开启消息返回确认，yml需要配置 publisher-returns: true
     * message    消息主体 message
     * replyCode  消息主体 message
     * replyText  描述
     * exchange   消息使用的交换机
     * routingKey 消息使用的路由键
     * <p>
     * PS：通过实现ReturnCallback接口，如果消息从交换机发送到对应队列失败时触发
     * </p>
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        String correlationId = message.getMessageProperties().getCorrelationId();
        log.debug("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}", correlationId, replyCode, replyText, exchange, routingKey);
    }
}
