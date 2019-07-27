package com.example.rabbitmq.sender;

import com.example.rabbitmq.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SimpleQueueSend {
    @Autowired
    private RabbitTemplate amqpTemplate;
//    private AmqpTemplate amqpTemplate;

    private User user;

    private static Logger log = LoggerFactory.getLogger(SimpleQueueSend.class);

    /**
     * simple发送端。
     */
    public void send(){
        user = new User();
        user.setPhone("1123456789");
        user.setAddress("广东");
        user.setAge(20);
        user.setGender("男");
        user.setName("张三");
        log.info("【simpleQueue】的发送端------->"+user);
        this.amqpTemplate.convertAndSend("simple",user); //给名为【simple】的队列发送【user】消息
    }

    /**
     * 使用【direct】交换器的发送端
     * @param user
     */
    public void directSend(User user){
        log.info("【direct】的发送端------>"+user);
        this.amqpTemplate.convertAndSend("direct",user);
    }

    /**
     * 【direct】交换器的发送端2
     * @param user
     */
    public void directSend2(User user){
        log.info("【direct】的发送端------>"+user);
        this.amqpTemplate.convertAndSend("direct2",user);
    }


    /**
     * 【topic】交换器的发送端
     * 第一个方法使用【topic.test.aaa】给【topic.test】发送消息
     * （消费者[可以]接收到）
     * @param user
     */
    public void topicSend(User user){
        log.info("【topic.test.aaa】的发送端------>"+user);
        this.amqpTemplate.convertAndSend("topicExchange","topic.test.aaa",user);

    }
    /**
     * 【topic】第二个方法
     * 使用【topic.test.aaa.bbb】给【topic.test】发送消息
     * （消费者[不能]接收到）
     * @param user
     */
    public void topicSend2(User user){
        CorrelationData correlationData = new CorrelationData();   //关联数据，假如消息丢失时可以用来查找是哪个丢失了，可以查bug
        correlationData.setId("随便给个id试试是什么效果");
        log.info("【topic.test.aaa.bbb】的发送端------>"+user);
        this.amqpTemplate.convertAndSend("topicExchange","topic.test.aaa.bbb",user,correlationData);
    }

    /**
     * 【topic】第三个方法
     * 使用【topic.msg.aaa】给【topic.msg】发送消息
     * (消费者[可以]接收到)
     * @param user
     */
    public void topicSend3(User user){
        log.info("【topic.msg.aaa】的发送端------>"+user);
        this.amqpTemplate.convertAndSend("topicExchange","topic.msg.aaa",user);
    }

    /**
     * 【topic】第四个方法
     * 使用【topic.msg.aaa.bbb】给【topic.msg】发送消息
     * (消费者[可以]接收到)
     * @param user
     */
    public void topicSend4(User user){
        log.info("【topic.msg.aaa.bbb】的发送端------>"+user);
        this.amqpTemplate.convertAndSend("topicExchange","topic.msg.aaa.bbb",user);
    }


    /**
     * 【fanout】的发送端
     * convertAndSend()方法的第一个参数是交换机的名称，
     *                     第二个参数是routingKey值，传空，因为传什么都当空用
     *                     第三个参数是数据
     * @param user
     */
    public void fanoutSend(User user){
        log.info("【fanout】的发送端------>"+user);
        this.amqpTemplate.convertAndSend("fanoutExchange","",user);
    }
}
