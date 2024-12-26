package udemy.rabbitmq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendHelloWorldMessage(String name) {
        rabbitTemplate.convertAndSend("course.hello", "Hello " + name);
    }
}
