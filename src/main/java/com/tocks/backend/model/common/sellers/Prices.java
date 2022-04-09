package com.tocks.backend.model.common.sellers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Prices {
    private Price price_min;
    private Price price_max;
    private Offer offers;
}
