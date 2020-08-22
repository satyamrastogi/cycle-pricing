package com.revamp.cyclepricing.utils;

import com.revamp.cyclepricing.model.CustomerPricing;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class CycleProducer implements Runnable {
    private final BlockingQueue<Map<String, List<CustomerPricing>>> blockingQueue;
    Map<String, List<CustomerPricing>> cycle;

    public CycleProducer(BlockingQueue<Map<String, List<CustomerPricing>>> blockingQueue, Map<String, List<CustomerPricing>> cycle) {
        this.blockingQueue = blockingQueue;
        this.cycle = cycle;
    }

    public void run() {
        try {
            blockingQueue.put(cycle);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
