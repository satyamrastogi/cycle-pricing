package com.revamp.cyclepricing.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revamp.cyclepricing.model.CustomerPricing;
import com.revamp.cyclepricing.model.StorePricingModel;
import com.revamp.cyclepricing.service.CyclePricingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("com.revamp.cyclepricing.service.impl.CyclePricingService")
public class CyclePricingServiceImpl implements CyclePricingService {

    private Map<String, Map<String, List<StorePricingModel>>> storePricing;

    @PostConstruct
    private void loadComponentsWithPrice() {
        log.info("construct the store pricing object");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = ResourceUtils.getFile("classpath:cyclePricing.json");
            storePricing = objectMapper.readValue(file,
                    new TypeReference<Map<String, Map<String, List<StorePricingModel>>>>() {
                    });
            log.info("storePricing constructed :{}", storePricing);
        } catch (IOException e) {
            log.error("Not able to convert the storePricing ", e);
        }
    }



    private Boolean isCorrectPricingDate(CustomerPricing inputPricingModel, StorePricingModel pricingModel) {
        if (inputPricingModel.getDate() >= pricingModel.getEffectiveDateFrom() &&
                inputPricingModel.getDate() <= pricingModel.getEffectiveDateTo())
            return Boolean.TRUE;
        return Boolean.FALSE;
    }

    @Override
    public Integer findThePriceOfACycle(Map<String, List<CustomerPricing>> input) {
        log.info("findThePriceOfACycle started with component :{}", input);
        Integer finalResult = Integer.valueOf(0);
        for (String set : input.keySet()) {
            Map<String, List<StorePricingModel>> result = storePricing.getOrDefault(set, new HashMap<>());
            List<CustomerPricing> modularComponentList = input.getOrDefault(set,new ArrayList<>());
            for (CustomerPricing inputPricingModel : modularComponentList) {
                List<StorePricingModel> storePricingList = result.getOrDefault(inputPricingModel.getName(), new ArrayList<>());
                if (!ObjectUtils.isEmpty(storePricingList)) {
                    for (StorePricingModel pricingModel : storePricingList) {
                        if (isCorrectPricingDate(inputPricingModel, pricingModel)) {
                            finalResult += pricingModel.getPrice();
                            break;
                        }
                    }
                }
            }
        }
        log.info("findThePriceOfACycle ended with result  :{}", finalResult);
        return finalResult;
    }
}
