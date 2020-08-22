package com.revamp.cyclepricing.service;

import com.revamp.cyclepricing.common.ResponseObject;
import com.revamp.cyclepricing.model.CustomerPricing;

import java.util.List;
import java.util.Map;
import java.util.Vector;

public interface ThreadingService {
    ResponseObject<Vector<String>> findTheResultOfCycles(List<Map<String, List<CustomerPricing>>> cycleList);
}
