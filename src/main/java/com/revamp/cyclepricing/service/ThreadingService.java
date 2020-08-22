package com.revamp.cyclepricing.service;

import com.revamp.cyclepricing.common.ResponseObject;
import com.revamp.cyclepricing.model.CustomerPricing;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

public interface ThreadingService {
    ResponseObject<ConcurrentMap<String,Integer>> findTheResultOfCycles(List<Map<String, List<CustomerPricing>>> cycleList);
}
