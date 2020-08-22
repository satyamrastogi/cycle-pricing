package com.revamp.cyclepricing;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revamp.cyclepricing.model.CustomerPricing;
import com.revamp.cyclepricing.service.impl.CyclePricingServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootTest
class CyclePricingApplicationTests {

	@Autowired
	private CyclePricingServiceImpl cyclePricingServiceImpl;

	@Test
	void cyclePricingTest() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		File file = ResourceUtils.getFile("classpath:input.json");
		Map<String, List<CustomerPricing>> input = objectMapper.readValue(file,
				new TypeReference<Map<String, List<CustomerPricing>>>() {
				});
		Integer result = cyclePricingServiceImpl.findThePriceOfACycle(input);
		Assert.isTrue(result.equals(Integer.valueOf(1376)),"Test Passed");
	}

}
