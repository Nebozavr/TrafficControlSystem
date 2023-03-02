package com.tcs.sensor.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@ToString
public class SensorData {

    private String sensorId;
    private SensorType sensorType;
    private LocalDateTime timestamp;
    private boolean available;
    private String dataValue;
}
