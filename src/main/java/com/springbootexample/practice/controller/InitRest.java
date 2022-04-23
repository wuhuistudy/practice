package com.springbootexample.practice.controller;

import com.google.gson.Gson;
import com.springbootexample.practice.entity.vo.MyTopics;
import com.springbootexample.practice.entity.vo.OrderPaidEvent;
import com.zc.smartcity.rocketmq.core.RocketMQTemplate;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

/**
 * @author wuhui
 * @date 2021年07月08日 下午 02:53
 */

@RestController
@RequestMapping("/mq")
public class InitRest {
    protected static Logger logger = LoggerFactory.getLogger(InitRest.class);

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 发送普通文本消息
     * http://localhost:9090/sendMsgText
     * @return
     */
    @RequestMapping("/sendMsgText")
    public String sendMsgText() {
        // 发送普通文本消息
        String msg = "Hello，World，spring-boot2-rocketmq! flag=" + new Random().nextInt(100);
        String destination1 = MyTopics.TOPIC1 + ":" + MyTopics.TAG1;
        rocketMQTemplate.convertAndSend(destination1, msg);
        if (logger.isDebugEnabled()){
            logger.debug("sendMsg success。发送普通文本消息 msg=" + msg);
        }
        return "success for text";
    }

    /**
     * 发送对象数据消息
     * http://localhost:9090/sendMsgObj
     * @return
     */
    @RequestMapping("/sendMsgObj")
    public String sendMsgObj() {
        // 发送对象数据消息
        OrderPaidEvent order = new OrderPaidEvent("T_001_ID_" + new Random().nextInt(100), new BigDecimal("88.00"));
        String destination2 = MyTopics.TOPIC1 + ":" + MyTopics.TAG2;
        rocketMQTemplate.convertAndSend(destination2, order);
        if (logger.isDebugEnabled()){
            logger.debug("sendMsg success。发送对象消息 order=" + order.toString());
        }

        return "success for obj";
    }

    @RequestMapping("/consumerMsg")
    public String consumerMsg() {
        try {
            // 指定消费组名
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("my-mq-group");

            // 指定服务器地址名
            consumer.setNamesrvAddr("121.5.200.64:9876");

            // 订阅一个或多个主题
            consumer.subscribe(MyTopics.TOPIC1, "*");

            // 注册回调
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    for (MessageExt msg : msgs) {
                        if (logger.isDebugEnabled()){
                            logger.debug("入口，消息tag=" + msg.getTags());
                        }

                        // 区分不同Tag，不同处理方式
                        switch (msg.getTags()) {

                            // 1. 普通文本消息
                            case MyTopics.TAG1:
                                if (logger.isDebugEnabled()){
                                    logger.debug("消息tag=" + msg.getTags() + "，消费者，消费数据【普通文本消息】：msg=" + new String(msg.getBody()));
                                }
                                break;

                            // 2. 对象数据消息
                            case MyTopics.TAG2:
                                Gson gson = new Gson();
                                OrderPaidEvent obj = gson.fromJson(new String(msg.getBody()), OrderPaidEvent.class);
                                if (logger.isDebugEnabled()){
                                    logger.debug("消息tag=" + msg.getTags() + "，消费者，消费数据【对象数据】：order=" + obj.toString());
                                }
                                break;

                        }
                    }

                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });

            // 启动消费者实例
            consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        if (logger.isDebugEnabled()){
            logger.debug("Consumer Started.");
        }
        return "---- consumer success ----";
    }

    /*
     * RocketMQ的最佳实践中推荐：一个应用尽可能用一个Topic，消息子类型用tags来标识，tags可以由应用自由设置。
     * 在使用rocketMQTemplate发送消息时，通过设置发送方法的destination参数来设置消息的目的地，
     * destination的格式为topicName:tagName，:前面表示topic的名称，后面表示tags名称。
     *
     * 注意: tags从命名来看像是一个复数，但发送消息时，目的地只能指定一个topic下的一个tag，不能指定多个。
     */
}
