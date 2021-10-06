package com.qf.listener;

import com.qf.constants.ShopConstants;
import com.qf.entity.Email;
import com.qf.service.IEmailService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.mail.MessagingException;
import java.io.IOException;

@Configuration
@Slf4j
public class EmailQueueListener {

    @Autowired
    private IEmailService emailService;

  @RabbitListener(queues = ShopConstants.EMAIL_QUEUE)
    public  void sendEmail(Email email, Channel channel, Message message) throws MessagingException {

     // BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

      System.out.println(email);
      emailService.sendEmail(email);
      log.debug("{}",email);
     // System.out.println("EMAILend");
      // 2.手动ack
      try {
          long deliveryTag = message.getMessageProperties().getDeliveryTag();  // 消息唯一标识
          channel.basicAck(deliveryTag,false);

      } catch (IOException e) {
          e.printStackTrace();
      }
  }

}
