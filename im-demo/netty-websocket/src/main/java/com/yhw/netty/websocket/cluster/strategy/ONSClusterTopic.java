package com.yhw.netty.websocket.cluster.strategy;

import cn.hutool.json.JSONUtil;
import com.aliyun.openservices.ons.api.*;
import com.yhw.netty.websocket.cluster.ImClusterTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * 采用阿里云，消息
 * @author yhw
 */
public class ONSClusterTopic implements ImClusterTopic {

    private static Logger logger = LoggerFactory.getLogger(ONSClusterTopic.class);

    private  Producer producer;

    private  String TOPIC;
    private  String GROUP_ID;
    private  String AccessKey;
    private  String SecretKey;


    public ONSClusterTopic(String TOPIC,String GROUP_ID,String AccessKey,String SecretKey){
        this.TOPIC = TOPIC;
        this.GROUP_ID = GROUP_ID;
        this.AccessKey = AccessKey;
        this.SecretKey = SecretKey;
        initProducer();
        consumer();
    }

    private void initProducer(){
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.GROUP_ID,GROUP_ID);
        properties.put(PropertyKeyConst.AccessKey,AccessKey);
        properties.put(PropertyKeyConst.SecretKey, SecretKey);

        // 广播订阅方式
        properties.put(PropertyKeyConst.MessageModel, PropertyValueConst.BROADCASTING);
        producer = ONSFactory.createProducer(properties);
//        producer = new ProducerImpl(properties);
        // 在发送消息前，必须调用 start 方法来启动 Producer，只需调用一次即可
        producer.start();

        publish(null);
    }



    @Override
    public void publish(com.yhw.netty.websocket.packets.Message message){
        //循环发送消息
        Message msg = new Message( //
                // 在控制台创建的 Topic，即该消息所属的 Topic 名称
                TOPIC,
                // Message Tag,
                // 可理解为 Gmail 中的标签，对消息进行再归类，方便 Consumer 指定过滤条件在消息队列 MQ 服务器过滤
                "ImMessage",
                // Message Body
                // 任何二进制形式的数据，消息队列 MQ 不做任何干预，
                // 需要 Producer 与 Consumer 协商好一致的序列化和反序列化方式
                "Hello MQ".getBytes());
        // 设置代表消息的业务关键属性，请尽可能全局唯一，以方便您在无法正常收到消息情况下，可通过控制台查询消息并补发
        // 注意：不设置也不会影响消息正常收发
        msg.setKey("ORDERID_100");
        // 发送消息，只要不抛异常就是成功
        // 打印 Message ID，以便用于消息发送状态查询
        SendResult sendResult = producer.send(msg);
        System.out.println("Send Message success. Message ID is: " + sendResult.getMessageId());

        // 在应用退出前，可以销毁 Producer 对象
        // 注意：如果不销毁也没有问题
    }

    private byte[] getMessageBody(com.yhw.netty.websocket.packets.Message message){
        return JSONUtil.toJsonStr(message).getBytes();
    }

    @Override
    public void consumer() {
        Properties properties = new Properties();

        properties.put(PropertyKeyConst.GROUP_ID,GROUP_ID);
        properties.put(PropertyKeyConst.AccessKey,AccessKey);
        properties.put(PropertyKeyConst.SecretKey, SecretKey);
        // 广播订阅方式设置
        properties.put(PropertyKeyConst.MessageModel, PropertyValueConst.BROADCASTING);

        Consumer consumer = ONSFactory.createConsumer(properties);

        //mq client recv mqtt msg ,just subscribe the parent topic
        consumer.subscribe(TOPIC, "*", new MessageListener() {
            @Override
            public Action consume(Message message, ConsumeContext consumeContext) {

                String msgContent = new String(message.getBody());
                logger.error("Send Message success. Message ID is: " + msgContent);
                return Action.CommitMessage;
            }
        });
        consumer.start();


        logger.info(this.getClass().toString()+"["+"TOPIC_IM_TEST"+"...Case Normal Consumer Init]   Ok");
       /* try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        consumer.shutdown();*/
    }
}
