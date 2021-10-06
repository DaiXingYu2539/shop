package com.qf.listener;


import com.qf.constants.ShopConstants;
import com.qf.entity.Goods;
import com.rabbitmq.client.Channel;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@Slf4j
public class ItemQueueListener {

    @Autowired
    private freemarker.template.Configuration configuration;

    private ExecutorService executorService = Executors.newFixedThreadPool(5);


    @RabbitListener(queues = ShopConstants.ITEM_QUEUE)
    public void createItem(Goods goods, Channel channel, Message message) throws IOException, TemplateException {

        executorService.submit(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                log.debug("{}", goods);
                // 1、获取模板
                Template template = configuration.getTemplate("goodsItemTemplate.ftl");

                // 2.准备数据
                Map<String, Object> map = new HashMap<>();
                map.put("gname", goods.getGname());
                map.put("gprice", goods.getGprice());
                map.put("pngList", goods.getTempPng().split("\\|"));

                // 3.准备静态也面输出的位置
                String path = ItemQueueListener.class.getClassLoader().getResource("static").getPath();

                // 4、生成静态也面
                template.process(map, new FileWriter(path + File.separator + goods.getId() + ".html"));

                // 5、手动ack
                try {
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }


}
