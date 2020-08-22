package com.revamp.cyclepricing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
@ToString
public class StorePricingModel {
    String objectName;
    Integer price;
    //taking input in long so that its easy to compare
    Long effectiveDateFrom;
    Long effectiveDateTo;
}
