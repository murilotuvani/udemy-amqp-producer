package udemy.rabbitmq.producer;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class FixedRateProducer {

    private static final Logger LOG = LoggerFactory.getLogger(FixedRateProducer.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private int messageNumber = 0;

    @Scheduled(fixedRate = 50000)
    public void sendMessage() {
        messageNumber++;
        String msg = "Fixed rate message " + messageNumber;
        rabbitTemplate.convertAndSend("course.fixedrate", msg);
        LOG.info("Message sent: " + msg);
    }

}
