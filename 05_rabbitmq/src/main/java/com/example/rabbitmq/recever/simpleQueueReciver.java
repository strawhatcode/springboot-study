package com.example.rabbitmq.recever;

import com.example.rabbitmq.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 模拟一个生产者对多个消费者（接收端都监听同一个队列）
 * 两个消费者会轮着来接收并处理生产者发过来信息
 */
@Component
public class simpleQueueReciver {
    private static Logger log = LoggerFactory.getLogger(simpleQueueReciver.class);

    /**
     * simple接收端【1】
     * @RabbitListener(queues = "simple")：监听队列名为【simple】的队列
     * @RabbitHandler 当收到发送过来的消息就执行下面方法
     * @param user  与发送时的数据类型一致
     */
    @RabbitListener(queues = "simple")
    @RabbitHandler
    public void receiver1(User user){
        log.info("【simple-receiver1】接收端收到的信息------>"+user);
    }

    /**
     * simple接收端【2】
     * @param user
     */
    @RabbitListener(queues = "simple")
    @RabbitHandler
    public void receiver2(User user){
        log.info("【simple-receiver2】接收端收到的信息------>"+user);

    }

    /**
     * 【direct】这个交换器绑定了两个队列【direct、direct2】
     * @param user
     */
    @RabbitListener(queues = {"direct","direct2"})
    @RabbitHandler
    public void directReceiver(User user){
        log.info("【direct-receiver】接收端收到的信息------>"+user);
    }


    /**
     * 【topic】的两个接收端
     * @param user
     */
    @RabbitListener(queues = "topic.test")
    @RabbitHandler
    public void topicReceiver(User user){
        log.info("【*通配符-->topic.test】接收端收到的信息------>"+user);
    }
    @RabbitListener(queues = "topic.msg")
    @RabbitHandler
    public void topicReceiver2(User user){
        log.info("【#通配符-->topic.msg】接收端收到的信息------>"+user);
    }


    /**
     * 【fanout】的三个接收端
     * @param user
     */
    @RabbitListener(queues = "fanoutQueue.aaa")
    @RabbitHandler
    public void fanoutReceiver(User user){
        log.info("【fanoutQueue.aaa】接收端收到的信息------>"+user);
    }
    @RabbitListener(queues = "fanoutQueue.bbb")
    @RabbitHandler
    public void fanoutReceiver2(User user){
        log.info("【fanoutQueue.bbb】接收端收到的信息------>"+user);
    }
    @RabbitListener(queues = "fanoutQueue.ccc")
    @RabbitHandler
    public void fanoutReceiver3(User user){
        log.info("【fanoutQueue.ccc】接收端收到的信息------>"+user);
    }
}
