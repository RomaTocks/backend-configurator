package com.tocks.backend.model.product.additional;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HDDAdditionalInformation extends Additional {
    private String volume;
    private String formFactor;
    private String buffer;
    private String spindleSpeed;
    private String readSpeed;
    private String writeSpeed;

}
