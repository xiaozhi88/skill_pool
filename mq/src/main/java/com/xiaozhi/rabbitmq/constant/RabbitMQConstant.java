package com.xiaozhi.rabbitmq.constant;

/**
 * @Description:
 * @Author: qjc
 * @Date: 2019/12/6
 */
public class RabbitMQConstant {

    //简单模式
    public static final String QUEUE_EASY = "easy.queue";
    //work模式
    public static final String QUEUE_WORK = "work.queue";
    //topic模式
    public static final String QUEUE_TOPIC_FIRST = "topic.queue.first";
    public static final String QUEUE_TOPIC_SECOND = "topic.queue.second";
    //发布订阅模式
    public static final String QUEUE_FANOUT = "fanout.queue";
    public static final String QUEUE_FANOUT_SECOND = "fanout.queue.second";

    //路由key
    public static final String ROUTING_KEY_EASY = "routing.key.easy";
    public static final String ROUTING_KEY_WORK = "routing.key.work";
    public static final String ROUTING_KEY_TOPIC_FIRST = "routing.key.topic.first";
    public static final String ROUTING_KEY_TOPIC_SECOND = "routing.key.topic.second";


    // direct交换机
    public static final String EXCHANGE_DIRECT = "direct_exchange";
    // work交换机
    public static final String EXCHANGE_WORK = "work_exchange";
    // topic交换机
    public static final String EXCHANGE_TOPIC = "topic_exchange";
    // fanout交换机
    public static final String EXCHANGE_FANOUT = "fanout_exchange";

}
