package org.camunda.support.kafka.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kafka")
public class UserResource {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "test-topic";

    @GetMapping("/publish/{name}")
    public String post(@PathVariable("name") final String name) {
        SendResult<String, String> result = null;
        try {
            result = kafkaTemplate.send(TOPIC, name).get();

        }
        catch (Exception e){
            e.printStackTrace();
        }

        String returnResult = "";
        if (result == null){
           throw new RuntimeException("Could not send Message");
        }
        else {
            returnResult = result.getProducerRecord().toString();
        }

        return returnResult;
    }
}
