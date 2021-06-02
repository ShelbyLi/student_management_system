package com.shelby.util.mq;

import com.shelby.entity.Student;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author Shelby Li
 * @Date 2021/6/2 18:42
 * @Version 1.0
 */

@Component
public class Sender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(Student student) {
        System.out.println("Sender : " + student);
        //使用AmqpTemplate将消息发送到消息队列QueueHello中去
        this.rabbitTemplate.convertAndSend("Queue1", student);
    }

}
