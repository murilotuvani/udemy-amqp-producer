package udemy.rabbitmq.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import udemy.rabbitmq.entity.Picture;

@Service
public class PictureProducerToTopic {

    private static final Logger LOG = LoggerFactory.getLogger(PictureProducerToTopic.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendMessage(Picture picture) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(picture);
        var sb = new StringBuilder();

        // web, mobile, etc
        sb.append(picture.getSource()).append(".");
        sb.append(picture.getSize()>4000 ? "large" : "small").append(".");
        sb.append(picture.getType());

        var routingKey = sb.toString();

        LOG.info("Sending to routing key : {}, data {}", routingKey, json);
        rabbitTemplate.convertAndSend("x.picture2", routingKey, json);
    }
}
