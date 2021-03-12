package com.xiaozhi.rabbitmq.config;

import com.xiaozhi.rabbitmq.callbackconfig.MsgSendConfirmCallback;
import com.xiaozhi.rabbitmq.callbackconfig.MsgSendReturnCallback;
import com.xiaozhi.rabbitmq.constant.RabbitMQConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: qjc
 * @Date: 2019/12/6
 */
@Configuration
@Slf4j
public class RabbitMqConfig {

    @Resource
    private QueueConfig queueConfig;
    @Resource
    private ExchangeConfig exchangeConfig;
    /**
     * 连接工厂
     */
    @Resource
    private ConnectionFactory connectionFactory;


    /**
     * 将消息队列和交换机进行绑定，指定路由
     */
    @Bean
    public Binding bindingDirect() {
        return BindingBuilder.bind(queueConfig.easyQueue()).to(exchangeConfig.directExchange()).with(RabbitMQConstant.ROUTING_KEY_EASY);
    }

    @Bean
    public Binding bindingWork() {
        return BindingBuilder.bind(queueConfig.workQueue()).to(exchangeConfig.workExchange()).with(RabbitMQConstant.ROUTING_KEY_WORK);
    }

    @Bean
    public Binding bindingTopic() {
        return BindingBuilder.bind(queueConfig.topicQueue()).to(exchangeConfig.topicExchange()).with(RabbitMQConstant.ROUTING_KEY_TOPIC_FIRST);
    }

    @Bean
    public Binding bindingTopicSecond() {
        return BindingBuilder.bind(queueConfig.topicQueueSecond()).to(exchangeConfig.topicExchange()).with(RabbitMQConstant.ROUTING_KEY_TOPIC_SECOND);
    }

    @Bean
    public Binding bindingFanout() {
        return BindingBuilder.bind(queueConfig.fanoutQueue()).to(exchangeConfig.fanoutExchange());
    }

    @Bean
    public Binding bindingFanoutSecond() {
        return BindingBuilder.bind(queueConfig.fanoutQueueSecond()).to(exchangeConfig.fanoutExchange());
    }

    /** ======================== 定制一些处理策略 =============================*/

    /**
     * 定制化amqp模版
     * <p>
     * Rabbit MQ的消息确认有两种。
     * <p>
     * 一种是消息发送确认：这种是用来确认生产者将消息发送给交换机，交换机传递给队列过程中，消息是否成功投递。
     * 发送确认分两步：一是确认是否到达交换机，二是确认是否到达队列
     * <p>
     * 第二种是消费接收确认：这种是确认消费者是否成功消费了队列中的消息。
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        // 消息发送失败返回到队列中, yml需要配置 publisher-returns: true
        rabbitTemplate.setMandatory(true);

        /**
         * 使用该功能需要开启消息确认，yml需要配置 publisher-confirms: true
         * 通过实现ConfirmCallBack接口，用于实现消息发送到交换机Exchange后接收ack回调
         * correlationData  消息唯一标志
         * ack              确认结果
         * cause            失败原因
         */
        rabbitTemplate.setConfirmCallback(new MsgSendConfirmCallback());
        /**
         * 使用该功能需要开启消息返回确认，yml需要配置 publisher-returns: true
         * 通过实现ReturnCallback接口，如果消息从交换机发送到对应队列失败时触发
         * message    消息主体 message
         * replyCode  消息主体 message
         * replyText  描述
         * exchange   消息使用的交换机
         * routingKey 消息使用的路由键
         */
        rabbitTemplate.setReturnCallback(new MsgSendReturnCallback());


        return rabbitTemplate;
    }

}
