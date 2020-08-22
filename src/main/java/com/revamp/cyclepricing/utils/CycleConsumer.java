package com.revamp.cyclepricing.utils;

import com.revamp.cyclepricing.model.CustomerPricing;
import com.revamp.cyclepricing.service.impl.CyclePricingServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Vector;

@Slf4j
public class CycleConsumer implements Runnable {

    Map<String, List<CustomerPricing>> cycleComponents;
    private final Vector<String> cyclePrice;
    private CyclePricingServiceImpl cyclePricingService;

    public CycleConsumer(Map<String, List<CustomerPricing>> cycleComponents,
                         Vector<String> cyclePrice, CyclePricingServiceImpl cyclePricingService) {
        this.cycleComponents=cycleComponents;
        this.cyclePrice = cyclePrice;
        this.cyclePricingService = cyclePricingService;
    }

    public void run() {
        String threadName = Thread.currentThread().getName().toLowerCase();
            log.info("Thread :{} is running ", threadName);
            Integer answer = cyclePricingService.findThePriceOfACycle(cycleComponents);
            cyclePrice.add(threadName+"-"+ answer.toString());
            log.info("Thread :{} is stopped ", threadName);
    }

}
