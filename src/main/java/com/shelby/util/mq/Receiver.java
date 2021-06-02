package com.shelby.util.mq;

import com.shelby.entity.ClubMember;
import com.shelby.entity.Student;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author Shelby Li
 * @Date 2021/6/2 19:34
 * @Version 1.0
 */

@Component
@RabbitListener(queues = "Queue1")  // 监听QueueHello的消息队列
public class Receiver {
    @RabbitHandler   // @RabbitHandler来实现具体消费
    public void QueueReceiver(Student student) {
        System.out.println("Receiver: " + student);
    }
}
