package udemy.rabbitmq.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import udemy.rabbitmq.entity.Picture;

@Service
public class PictureProducerWithError {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendMessage(Picture picture) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(picture);
        rabbitTemplate.convertAndSend("x.mypicture", picture.getType(), json);
    }

}
