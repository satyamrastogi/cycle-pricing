package com.revamp.cyclepricing.service;

import com.revamp.cyclepricing.model.CustomerPricing;

import java.util.List;
import java.util.Map;

public interface CyclePricingService {
    Integer findThePriceOfACycle(Map<String, List<CustomerPricing>> input);
}
