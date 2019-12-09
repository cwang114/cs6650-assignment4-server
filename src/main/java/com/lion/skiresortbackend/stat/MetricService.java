package com.lion.skiresortbackend.stat;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Service;

@Service
public class MetricService {
 
    private ConcurrentMap<String, ConcurrentHashMap<Integer, Integer>> metricMap;
 
    public void increaseCount(String request, int status) {
        ConcurrentHashMap<Integer, Integer> statusMap = metricMap.get(request);
        if (statusMap == null) {
            statusMap = new ConcurrentHashMap<Integer, Integer>();
        }
 
        Integer count = statusMap.get(status);
        if (count == null) {
            count = 1;
        } else {
            count++;
        }
        statusMap.put(status, count);
        metricMap.put(request, statusMap);
    }
 
    public Map getFullMetric() {
        return metricMap;
    }
}
