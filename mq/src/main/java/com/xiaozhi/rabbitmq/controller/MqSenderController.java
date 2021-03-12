package com.xiaozhi.rabbitmq.controller;

import com.xiaozhi.rabbitmq.mqsender.direct.DirectSender;
import com.xiaozhi.rabbitmq.mqsender.fanout.FanoutSender;
import com.xiaozhi.rabbitmq.mqsender.topic.TopicSender;
import com.xiaozhi.rabbitmq.mqsender.work.WorkSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @Description:
 * @Author: qjc
 * @Date: 2019/12/6
 */
@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class MqSenderController {

    /**
     * rabbitMQ 的核心就是  交换器,路由键,队列;
     * <p>
     * 消息来了之后,会发送到交换器,交换器将根据路由键将消息发送到相对应的队列里面去!
     * <p>
     * 消息不用关系到达了那个队列.只需要带着自己的路由键到了交换器就可以了,交换器会帮你把消息发送到指定的队列里面去!
     */

    @Resource
    DirectSender directSender;
    @Resource
    WorkSender workSender;
    @Resource
    TopicSender topicSender;
    @Resource
    FanoutSender fanoutSender;

    /**
     * 测试简单模式
     */
    @RequestMapping(value = "/direct/sender", method = {RequestMethod.GET})
    public void directSender() {
        for (int i = 0; i < 1; i++) {
            directSender.send(i);
        }
    }

    /**
     * 测试work模式
     */
    @RequestMapping(value = "/work/sender", method = {RequestMethod.GET})
    public void workSender() {
        for (int i = 0; i < 10; i++) {
            workSender.send(i);
        }
    }

    /**
     * 测试topic模式
     */
    @RequestMapping(value = "/topic/sender", method = {RequestMethod.GET})
    public void topicSender() {
        for (int i = 0; i < 10; i++) {
            topicSender.send(i);
        }
    }

    /**
     * 测试fanout模式
     */
    @RequestMapping(value = "/fanout/sender", method = {RequestMethod.GET})
    public void fanoutSender() {
        for (int i = 0; i < 10; i++) {
            fanoutSender.send(i);
        }
    }


}
