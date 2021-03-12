package com.xiaozhi.rabbitmq.callbackconfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: qjc
 * @Date: 2019/12/10
 */
@Component
@Slf4j
public class MsgSendConfirmCallback implements RabbitTemplate.ConfirmCallback {

    /**
     * 使用该功能需要开启消息确认，yml需要配置 publisher-confirms: true
     * correlationData  消息唯一标志
     * ack              确认结果
     * cause            失败原因
     * <p>
     * PS：通过实现ConfirmCallBack接口，用于实现消息发送到交换机Exchange后接收ack回调
     * </p>
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.debug("消息发送到exchange成功,id: {}", correlationData.getId());
        } else {
            log.debug("消息{}发送到exchange失败,原因: {}", correlationData.getId(), cause);
        }
    }
}
