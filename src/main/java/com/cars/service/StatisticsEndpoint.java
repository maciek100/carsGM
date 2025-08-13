package com.cars.service;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Endpoint(id = "stats")
public class StatisticsEndpoint {

    private final CarOperationCounters carOperationCounters;
    public StatisticsEndpoint (CarOperationCounters carOperationCounters) {
        this.carOperationCounters = carOperationCounters;
    }
    @ReadOperation
    public Map<String, Long> presentStatistics () {
        Map<String, Long> orderedMap = new LinkedHashMap<>();
        orderedMap.put("Cars Initially", carOperationCounters.getInitialCarCount());
        orderedMap.put("Cars Added", carOperationCounters.getCarAddCount());
        orderedMap.put("Cars Deleted", carOperationCounters.getCarDeleteCount());
        orderedMap.put("Cars Listed", carOperationCounters.getCarListCount());
        return orderedMap;
    }
}
