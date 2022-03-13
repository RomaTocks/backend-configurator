package com.tocks.backend.model.common.additional;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Rating
{
    private Integer rating;
    private Integer count;
}
