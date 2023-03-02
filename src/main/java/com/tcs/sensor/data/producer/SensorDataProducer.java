package com.tcs.sensor.data.producer;

import com.tcs.sensor.data.model.SensorData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SensorDataProducer {

    private static final String TOPIC = "SensorData";

    private final KafkaTemplate<String, SensorData> template;

    public String sendTopic(SensorData sensorData) {

        template.send(TOPIC, sensorData);
        log.info("Produced:\ntopic: {}", sensorData);

        return "Published Successfully";
    }

}
