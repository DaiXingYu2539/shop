package com.qf.service.Impl;

import com.qf.entity.Email;
import com.qf.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromUser;

    @Override
    public void sendEmail(Email email) throws MessagingException {
       System.out.println(email);
        // 1.创建一个邮件发送类
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        // 2.创建一个邮件的模板
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setSubject(email.getTitle()); // 标题
        helper.setTo(email.getToUser()); // 收件人
        helper.setFrom(fromUser); // 发件人
        helper.setText(email.getContent(),true);
        if(StringUtils.isEmpty(email.getCcUser())){
            helper.setCc(new String[]{fromUser}); // 163在发送邮件的时候把自己的抄上
        }else{
            helper.setCc(new String[]{fromUser,email.getCcUser()}); // 163在发送邮件的时候把自己的抄上
        }

        // 3.发送
        mailSender.send(mimeMessage);
    }
}
