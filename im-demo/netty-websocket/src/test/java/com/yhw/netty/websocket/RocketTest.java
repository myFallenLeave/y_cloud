//package com.yhw.netty.websocket;
//
//import org.apache.rocketmq.client.producer.DefaultMQProducer;
//import org.apache.rocketmq.client.producer.SendResult;
//import org.apache.rocketmq.common.message.Message;
//import org.apache.rocketmq.remoting.common.RemotingHelper;
//
//public class RocketTest {
//
//    public static void main(String[] args) throws Exception {
//        //Instantiate with a producer group name.
//        DefaultMQProducer producer = new
//                DefaultMQProducer("please_rename_unique_group_name");
//        // Specify name server addresses.
//        producer.setNamesrvAddr("192.168.224.129:9876");
//        //Launch the instance.
//        producer.start();
//
//        Message msg = new Message("TopicTest" /* Topic */,
//                "TagA" /* Tag */,
//                ("Hello RocketMQ ").getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
//        );
//        //Call send message to deliver message to one of brokers.
//        producer.sendOneway(msg);
////        producer.createTopic("AUTO_CREATE_TOPIC_KEY","TopicTest",2);
//       /* for (int i = 0; i < 100; i++) {
//            //Create a message instance, specifying topic, tag and message body.
//            Message msg = new Message("TopicTest" *//* Topic *//*,
//                    "TagA" *//* Tag *//*,
//                    ("Hello RocketMQ " +
//                            i).getBytes(RemotingHelper.DEFAULT_CHARSET) *//* Message body *//*
//            );
//            //Call send message to deliver message to one of brokers.
//            SendResult sendResult = producer.send(msg,60*60);
//            System.out.printf("%s%n", sendResult);
//        }
//        //Shut down once the producer instance is not longer in use.
//        producer.shutdown();*/
//    }
//}
