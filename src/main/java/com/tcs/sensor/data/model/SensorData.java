package com.tcs.sensor.data.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@ToString
@NoArgsConstructor
public class SensorData {

    private String sensorId;
    private SensorType sensorType;
    private LocalDateTime timestamp;
    private boolean available;
    private String dataValue;
}
