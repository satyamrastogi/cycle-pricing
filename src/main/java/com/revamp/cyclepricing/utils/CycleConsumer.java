package com.revamp.cyclepricing.utils;

import com.revamp.cyclepricing.model.CustomerPricing;
import com.revamp.cyclepricing.service.CyclePricingService;
import com.revamp.cyclepricing.service.impl.CyclePricingServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;

@Slf4j
public class CycleConsumer implements Runnable {

    private final BlockingQueue<Map<String, List<CustomerPricing>>> blockingQueue;
    private final ConcurrentMap<String, Integer> cyclePrice;
    private CyclePricingServiceImpl cyclePricingService;

    public CycleConsumer(BlockingQueue<Map<String, List<CustomerPricing>>> blockingQueue,
                         ConcurrentMap<String, Integer> cyclePrice,CyclePricingServiceImpl cyclePricingService) {
        this.blockingQueue = blockingQueue;
        this.cyclePrice = cyclePrice;
        this.cyclePricingService = cyclePricingService;
    }

    public void run() {
        String threadName = Thread.currentThread().getName().toLowerCase();
        try {
            log.info("Thread :{} is running ", threadName);
            Map<String, List<CustomerPricing>> result = blockingQueue.take();
            Integer answer = cyclePricingService.findThePriceOfACycle(result);
            cyclePrice.put(threadName, answer);
            log.info("Thread :{} is stopped ", threadName);
        } catch (InterruptedException e) {
            log.error("Thread :{} interrupted", threadName);
            Thread.currentThread().interrupt();
        }
    }

}
