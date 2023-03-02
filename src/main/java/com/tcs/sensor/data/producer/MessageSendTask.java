package com.tcs.sensor.data.producer;

import com.tcs.sensor.data.model.RoadSensorDataValue;
import com.tcs.sensor.data.model.SensorData;
import com.tcs.sensor.data.model.SensorType;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
public class MessageSendTask {

    public static final List<String> sensorIds = Arrays.asList("ID_1", "ID_2");

    private final SensorDataProducer producer;
    private final Map<String, SensorData> sensorCache = new HashMap<>();

    @Scheduled(fixedRateString = "3000")
    public void sendMessage() {
        SensorData sensorData = sensorCache.computeIfAbsent("ID_1", k -> SensorData.builder().sensorId(k).sensorType(SensorType.ROAD_SENSOR).build());

        sensorData.setTimestamp(LocalDateTime.now());
        Random random = new Random();
        //boolean available = random.nextBoolean();
        boolean available = true;

        sensorData.setAvailable(available);
        if (available) {
            sensorData.setDataValue(RoadSensorDataValue.randomValue().name());
        } else {
            sensorData.setDataValue(null);
        }

        producer.sendTopic(sensorData);
    }
}
