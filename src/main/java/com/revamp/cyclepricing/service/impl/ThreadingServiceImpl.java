package com.revamp.cyclepricing.service.impl;

import com.revamp.cyclepricing.common.ResponseObject;
import com.revamp.cyclepricing.model.CustomerPricing;
import com.revamp.cyclepricing.service.ThreadingService;
import com.revamp.cyclepricing.utils.CustomThreadExecutor;
import com.revamp.cyclepricing.utils.CycleConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.*;

@Service("com.revamp.cyclepricing.service.impl.ThreadingService")
@Slf4j
public class ThreadingServiceImpl implements ThreadingService {

    @Autowired
    private CyclePricingServiceImpl cyclePricingService;

    @Override
    public ResponseObject<Vector<String>> findTheResultOfCycles(List<Map<String, List<CustomerPricing>>> cycleList) {

        ResponseObject<Vector<String>> responseObject = new ResponseObject<>();
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(10);
        Vector<String> cyclePrice = new Vector<>();
        CustomThreadExecutor executor = new CustomThreadExecutor(10,
                10, 5000, TimeUnit.MILLISECONDS, blockingQueue);
        executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r,
                                          ThreadPoolExecutor executor) {
                log.info("Waiting for a second !!");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                executor.execute(r);
            }
        });
        executor.prestartAllCoreThreads();
        for(int i=0;i<cycleList.size();i++) {
            executor.execute(new CycleConsumer(cycleList.get(i),cyclePrice,cyclePricingService));
        }

        responseObject.setResponse(cyclePrice);
        responseObject.setResponseStatus("Success");
        responseObject.setStatus(HttpStatus.OK);
        return responseObject;
    }

}
