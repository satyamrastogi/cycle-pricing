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

public class CustomerPricing {
    //component Name like handle ,bars,tyre etc
    String name;
    //Date on which you have to create a bill
    Long date;
}
