package com.xiaozhi.rabbitmq.config;

import com.xiaozhi.rabbitmq.constant.RabbitMQConstant;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: qjc
 * @Date: 2019/12/6
 */
@Configuration
public class QueueConfig {

    /**
     * durable="true" 持久化 rabbitmq重启的时候不需要创建新的队列
     * exclusive 表示该消息队列是否只在当前connection生效,默认是false
     * auto-delete 表示消息队列没有在使用时将被自动删除 默认是false
     */

    @Bean(name = RabbitMQConstant.QUEUE_EASY)
    public Queue easyQueue() {
        return new Queue(RabbitMQConstant.QUEUE_EASY, true, false, false);
    }

    @Bean(name = RabbitMQConstant.QUEUE_WORK)
    public Queue workQueue() {
        return new Queue(RabbitMQConstant.QUEUE_WORK, true, false, false);
    }

    @Bean(name = RabbitMQConstant.QUEUE_TOPIC_FIRST)
    public Queue topicQueue() {
        return new Queue(RabbitMQConstant.QUEUE_TOPIC_FIRST, true, false, false);
    }

    @Bean(name = RabbitMQConstant.QUEUE_TOPIC_SECOND)
    public Queue topicQueueSecond() {
        return new Queue(RabbitMQConstant.QUEUE_TOPIC_SECOND, true, false, false);
    }

    @Bean(name = RabbitMQConstant.QUEUE_FANOUT)
    public Queue fanoutQueue() {
        return new Queue(RabbitMQConstant.QUEUE_FANOUT, true, false, false);
    }

    @Bean(name = RabbitMQConstant.QUEUE_FANOUT_SECOND)
    public Queue fanoutQueueSecond() {
        return new Queue(RabbitMQConstant.QUEUE_FANOUT_SECOND, true, false, false);
    }


}
