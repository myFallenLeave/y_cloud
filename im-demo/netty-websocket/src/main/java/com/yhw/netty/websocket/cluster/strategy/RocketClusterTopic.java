//package com.yhw.netty.websocket.cluster.strategy;
//
//import com.yhw.netty.websocket.cluster.ImClusterTopic;
//import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
//import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
//import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
//import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
//import org.apache.rocketmq.client.exception.MQBrokerException;
//import org.apache.rocketmq.client.exception.MQClientException;
//import org.apache.rocketmq.client.producer.DefaultMQProducer;
//import org.apache.rocketmq.client.producer.SendResult;
//import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
//import org.apache.rocketmq.common.message.Message;
//import org.apache.rocketmq.common.message.MessageExt;
//import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
//import org.apache.rocketmq.remoting.common.RemotingHelper;
//import org.apache.rocketmq.remoting.exception.RemotingException;
//
//import java.io.UnsupportedEncodingException;
//import java.util.List;
//
///**
// * 基于rocket的消息订阅模式
// * @author yhw
// */
//public class RocketClusterTopic implements ImClusterTopic {
//
//    /**
//     * 主题
//     */
//    private String topic;
//    /**
//     * 消费id
//     */
//    private String consumerId;
//    //key
//    private String accessKey;
//    //秘钥
//    private String secretKey;
//
//    public RocketClusterTopic(){
//
//    }
//
//
//    @Override
//    public void publish(com.yhw.netty.websocket.packets.Message message){
//        DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName");
//        try {
//            producer.start();
//        } catch (MQClientException e) {
//            e.printStackTrace();
//        }
//
//        for (int i = 0; i < 100; i++){
//            SendResult sendResult = null;
//
//            try {
//                Message msg = new Message("TopicTest",
//                        "TagA",
//                        "OrderID188",
//                        "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
//                sendResult = producer.send(msg);
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            } catch (MQClientException e) {
//                e.printStackTrace();
//            } catch (RemotingException e) {
//                e.printStackTrace();
//            } catch (MQBrokerException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            System.out.printf("%s%n", sendResult);
//        }
//        producer.shutdown();
//    }
//
//    @Override
//    public void consumer() {
//        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("example_group_name");
//
//        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
//
//        //set to broadcast mode
//        consumer.setMessageModel(MessageModel.BROADCASTING);
//
//        try {
//            consumer.subscribe("TopicTest", "TagA || TagC || TagD");
//        } catch (MQClientException e) {
//            e.printStackTrace();
//        }
//
//        consumer.registerMessageListener(new MessageListenerConcurrently() {
//
//            @Override
//            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
//                                                            ConsumeConcurrentlyContext context) {
//                System.out.printf(Thread.currentThread().getName() + " Receive New Messages: " + msgs + "%n");
//                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//            }
//        });
//
//        try {
//            consumer.start();
//        } catch (MQClientException e) {
//            e.printStackTrace();
//        }
//        System.out.printf("Broadcast Consumer Started.%n");
//    }
//}
