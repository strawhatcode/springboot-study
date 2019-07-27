package com.example.rabbitmq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class rabbitMQConfig {
    private static Logger log = LoggerFactory.getLogger(rabbitMQConfig.class);

    @Autowired
    private CachingConnectionFactory connectionFactory;     //RabbitTemplate使用CachingConnectionFactory作为连接工厂


    /**
     * 无交换器
     * new Queue(name,durable,exclusive,autoDelete)
     *      -name:队列名称
     *      -durable：持久化，默认true
     *      -exclusive：是否排他，默认false，是否把队列设置为排他队列，如果为true，连接断开时会删除
     *      -autoDelete：自动删除，默认false，所有与这个队列连接的消费者都断开时才会删除
     * @return
     */
    @Bean
    public Queue simpleQueue(){
        return new Queue("simple",true,false,false);
    }


    /**
     * 使用【direct】交换器
     * 【direct】是rabbitMQ默认的交换机模式，也是最简单的模式，
     * 即创建消息队列的时候，指定一个bindingKey，当发送者发送消息的时候，
     * 指定对应key，当key和消息队列的bindingKey一致的时候，消息会被发送到该消息队列中
     * @return
     */
    @Bean
    public Queue directQueue(){
        return new Queue("direct");
    }

    /**
     * DirectExchange(name,durable,autoDelete)
     *      -name:交换器名字
     *      -durable:是否持久化，默认true
     *      -autoDelete:是否自动删除，默认false
     * @return
     */
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("directExchange",true,false);
    }
    @Bean
    public Binding bindingDirectExchangeQueue(){
        return BindingBuilder.bind(directQueue()).to(directExchange()).with("direct");
    }

    /**
     * 【direct】绑定多个队列，每个队列都有特定的【routingKey】
     * @return
     */
    @Bean
    public Queue directQueue2(){
        return new Queue("direct2");
    }
    @Bean
    public Binding bindingDirectExchangeQueue2(){
        return BindingBuilder.bind(directQueue2()).to(directExchange()).with("direct2");
    }


    /**
     * 【topicExchange】交换器
     * 【topic】交换器主要依据是使用通配符（*、#），队列和交换机的绑定主要是依据一种模式(字符串+通配符)，
     * 而当发送消息时，只有当指定的key和该模式匹配的时候,消息才会被发送到该消息的队列中
     * 【*】：匹配key后面一段【topic.test.*】可以匹配【topic.test.aaa】、【topic.test.bbb】等
     *      不能匹配【topic.test.aaa.xxx】或者后面更多段
     * 【#】：模糊匹配，即只要有【topic.msg】，后面无论还有多少内容，都可以匹配得到
     *
     * 这里一个交换器绑定两个队列，分别测试两种通配符方式的匹配
     * @return
     */
    @Bean("topicqueue")
    public Queue topicQueue(){
        return new Queue("topic.test");
    }
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange("topicExchange");
    }
    @Bean   //另外一种绑定方法，使用@Bean---@Qualifier方式来匹配
    public Binding bindingTopicExchangeQueue(@Qualifier("topicqueue") Queue topic, TopicExchange topicExchange){
        return BindingBuilder.bind(topic).to(topicExchange).with("topic.test.*");
    }
    @Bean
    public Queue topicQueue2(){
        return new Queue("topic.msg");
    }
    @Bean
    public Binding bindingTopicExchangeQueue2(){
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("topic.msg.#");
    }


    /**
     * 【fanout】是路由广播的形式，把发送端发送的信息全部发给绑定它的队列，
     * routingkey不用设置，就算设置了没用
     * 这里一个【fanout】交换器绑定了3个队列
     * @return
     */
    @Bean
    public Queue fanoutQueue(){
        return new Queue("fanoutQueue.aaa");
    }
    @Bean
    public Queue fanoutQueue2(){
        return new Queue("fanoutQueue.bbb");
    }
    @Bean
    public Queue fanoutQueue3(){
        return new Queue("fanoutQueue.ccc");
    }
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("fanoutExchange");
    }
    @Bean
    public Binding bindingFanoutExchangeQueue(){
        return BindingBuilder.bind(fanoutQueue()).to(fanoutExchange());
    }
    @Bean
    public Binding bindingFanoutExchangeQueue2(){
        return BindingBuilder.bind(fanoutQueue2()).to(fanoutExchange());
    }
    @Bean
    public Binding bindingFanoutExchangeQueue3(){
        return BindingBuilder.bind(fanoutQueue3()).to(fanoutExchange());
    }


    /**
     * 设置模版
     *
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMandatory(true);
//        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());


        //消息发送成功的回调，也可以写在【消息发送】的类中，必须开启publisher-confirms: true
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack){
                log.info("发送消息成功：correlationData({}),ack({}),cause({})",correlationData,ack,cause);
            }else {
                log.info("发送消息失败：correlationData({}),ack({}),cause({})",correlationData,ack,cause);
            }
        });

        //消息丢失时的回调，必须开启publisher-returns: true、mandatory: true
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) ->
                log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}",
                exchange,routingKey,replyCode,replyText,message));
        return rabbitTemplate;
    }

}
