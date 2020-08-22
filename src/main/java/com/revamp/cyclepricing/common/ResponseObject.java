package com.revamp.cyclepricing.common;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode

public class ResponseObject<T> {
    private T response;
    private String responseStatus;
    private HttpStatus status;

    public ResponseObject(String responseStatus, HttpStatus status) {
        this.responseStatus = responseStatus;
        this.status = status;
    }
}
