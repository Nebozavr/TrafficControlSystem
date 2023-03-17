package com.tcs.sensor.data.producer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.sensor.data.model.RoadSensorDataValue;
import com.tcs.sensor.data.model.SensorData;
import com.tcs.sensor.data.model.SensorType;
import com.tcs.sensor.data.model.TrafficLightsSensorDataValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;

import static com.tcs.sensor.data.model.TrafficLightsSensorDataValue.*;

@Component
@Slf4j
public class MessageSendTask {

    private final Map<String, SensorData> sensorCache = new HashMap<>();

    private List<SensorData> roadSensors;
    private List<SensorData> lightSensors;

    private final SensorDataProducer producer;

    private TrafficLightsSensorDataValue currentLight = randomValue();

    @Autowired
    public MessageSendTask(SensorDataProducer producer) {
        this.producer = producer;

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            InputStream roadSensorsFile = getClass().getClassLoader().getResourceAsStream("road_sensors.json");
            InputStream trafficLightsFile = getClass().getClassLoader().getResourceAsStream("traffic_lights_sensors.json");
            //File roadSensorsFile = new File("road_sensors.json");
            //File trafficLightsFile = new File("traffic_lights_sensors.json");

            roadSensors = objectMapper.readValue(roadSensorsFile, new TypeReference<>() {
            });
            lightSensors = objectMapper.readValue(trafficLightsFile, new TypeReference<>() {
            });
        } catch (IOException e) {
            log.error(e.getMessage());
        }

    }

    @Scheduled(fixedRateString = "5000")
    public void sendRoadSensorsData() {
        for (SensorData sensorData : roadSensors) {

            sensorData.setTimestamp(LocalDateTime.now());
            //Random random = new Random();
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

    @Scheduled(fixedRateString = "10000")
    public void sendTrafficLightSensorsData() {
        for (SensorData sensorData : lightSensors) {

            sensorData.setTimestamp(LocalDateTime.now());
            //Random random = new Random();
            //boolean available = random.nextBoolean();
            boolean available = true;

            sensorData.setAvailable(available);
            if (available) {
                TrafficLightsSensorDataValue data = currentLight.equals(RED) ? GREEN : RED;
                sensorData.setDataValue(data.name());
                currentLight = data;
            } else {
                sensorData.setDataValue(null);
            }

            producer.sendTopic(sensorData);
        }
    }
}
