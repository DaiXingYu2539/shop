package com.qf.config;

import com.qf.constants.ShopConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    //消息队列
    @Bean
    public Queue itemQueue(){
        return new Queue(ShopConstants.ITEM_QUEUE,true,false,false);
    }
    // 交换机
    @Bean
    public TopicExchange goodsExchange(){
        return new TopicExchange(ShopConstants.GOODS_EXCHANGE,true,false);
    }
    //绑定
    @Bean
    public Binding goodsQueueTogoodsExchange(){
        return BindingBuilder.bind(itemQueue()).to(goodsExchange()).with("goods.*");
    }

}
