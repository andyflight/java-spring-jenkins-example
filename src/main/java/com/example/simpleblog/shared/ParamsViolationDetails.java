package com.example.simpleblog.shared;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder(toBuilder = true)
@Jacksonized
public record ParamsViolationDetails(
        String fieldName,
        String reason
) {

}