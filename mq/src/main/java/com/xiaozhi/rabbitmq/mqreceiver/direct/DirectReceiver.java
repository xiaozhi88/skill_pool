package com.xiaozhi.rabbitmq.mqreceiver.direct;

import com.rabbitmq.client.Channel;
import com.xiaozhi.rabbitmq.constant.RabbitMQConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: qjc
 * @Date: 2019/12/6
 */
@Component
@Slf4j
public class DirectReceiver {

    @RabbitListener(queues = RabbitMQConstant.QUEUE_EASY)
    public void process(Message message, Channel channel) throws Exception {
        String messageContent = new String(message.getBody());
        log.info("消费者接收消息: " + messageContent);
        // deliveryTag可以唯一确认该队列中的一条消息，从1开始递增相当于数据库的自增主键
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        log.info("索引为{}的消息被消费", deliveryTag);
        try {
//            int a = 1 / 0;
            /**
             * void basicAck(long deliveryTag, boolean multiple) throws IOException;
             * deliveryTag：该消息的index
             * multiple：是否批量处理.true:将一次性ack所有小于deliveryTag的消息
             */
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error("消费消息：" + messageContent + "——>出现异常", e);
            /**
             * void basicNack(long deliveryTag, boolean multiple, boolean requeue) throws IOException;
             *  deliveryTag:该消息的index
             *  multiple：是否批量.true:将一次性拒绝所有小于deliveryTag的消息
             * <p>
             *  requeue：被拒绝的是否重新入队列，如果设置为true ，则会添加在队列的末端
             *      PS: 此时最好限制一下异常多少次后(可以用redis计数)不再重新入队列，或者发邮件通知开发人员,否则有可能会一直异常下去
             * </p>
             */
//            channel.basicNack(deliveryTag, false, true);

            // 如果捕获异常，重试机制会失效，所以最终还是需要抛出异常，才能进行重试消费
            throw new Exception("消费异常");
        }
    }


}
