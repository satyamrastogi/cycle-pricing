package com.revamp.cyclepricing.service.impl;

import com.revamp.cyclepricing.common.ResponseObject;
import com.revamp.cyclepricing.model.CustomerPricing;
import com.revamp.cyclepricing.service.CyclePricingService;
import com.revamp.cyclepricing.service.ThreadingService;
import com.revamp.cyclepricing.utils.CycleConsumer;
import com.revamp.cyclepricing.utils.CycleProducer;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

@Service("com.revamp.cyclepricing.service.impl.ThreadingService")
public class ThreadingServiceImpl implements ThreadingService {

    @Autowired
    private CyclePricingServiceImpl cyclePricingService;

    @Override
    public ResponseObject<ConcurrentMap<String, Integer>> findTheResultOfCycles(List<Map<String, List<CustomerPricing>>> cycleList) {

        ResponseObject<ConcurrentMap<String, Integer>> responseObject = new ResponseObject<>();
        ConcurrentMap<String, Integer> cyclePrice = new ConcurrentHashMap<>();
        BlockingQueue<Map<String, List<CustomerPricing>>> queue = new LinkedBlockingQueue<>(10);

        for (int i = 0; i < cycleList.size(); i++) {
            new Thread(new CycleProducer(queue, cycleList.get(i))).start();
        }
        for(int i = 0; i < cycleList.size(); i++){
            new Thread(new CycleConsumer(queue, cyclePrice,cyclePricingService)).start();
        }
        responseObject.setResponse(cyclePrice);
        responseObject.setResponseStatus("Success");
        responseObject.setStatus(HttpStatus.OK);
        return responseObject;
    }

}
