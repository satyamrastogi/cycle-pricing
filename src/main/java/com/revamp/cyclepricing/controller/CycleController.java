package com.revamp.cyclepricing.controller;

import com.revamp.cyclepricing.common.ResponseObject;
import com.revamp.cyclepricing.model.CustomerPricing;
import com.revamp.cyclepricing.service.impl.ThreadingServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@RestController
public class CycleController {

    @Autowired
    @Qualifier("com.revamp.cyclepricing.service.impl.ThreadingService")
    private ThreadingServiceImpl threadingService;

    @GetMapping("/cycle/pricing")
    public ResponseEntity<ResponseObject<ConcurrentMap<String,Integer>>> findThePriceOfCycleList(@RequestBody ResponseObject<List<Map<String, List<CustomerPricing>>>> cycleList){
        log.info("findThePriceOfCycleList with requestBody :{}",cycleList);
        ResponseObject<ConcurrentMap<String,Integer>> responseObject = new ResponseObject<>();
        responseObject = threadingService.findTheResultOfCycles(cycleList.getResponse());
        log.info("findThePriceOfCycleList with response :{}",responseObject);
        return ResponseEntity.status(responseObject.getStatus()).body(responseObject);
    }


}
