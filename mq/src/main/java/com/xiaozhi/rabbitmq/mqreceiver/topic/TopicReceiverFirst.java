package com.xiaozhi.rabbitmq.mqreceiver.topic;

import com.xiaozhi.rabbitmq.constant.RabbitMQConstant;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: qjc
 * @Date: 2019/12/6
 */
@Component
@RabbitListener(queues = RabbitMQConstant.QUEUE_TOPIC_FIRST)
public class TopicReceiverFirst {

    @RabbitHandler
    public void process(Integer neo) {
//        int a = 1 / 0;
        try {
            // 设置消费者1处理消息用时**ms，另外一个消费者2处理消息用时***ms，由于消费者1消费速度快，所以消费者1可以执行更多的任务。
            Thread.sleep(1000);
            System.err.println("消费者11111接收到的消息: " + neo);
        } catch (Exception e) {
            System.err.println("消费者11111接收消息出现异常: -----" + neo);
            e.printStackTrace();
        }
    }


}
