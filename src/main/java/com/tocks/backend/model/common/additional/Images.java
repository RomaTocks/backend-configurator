package com.tocks.backend.model.common.additional;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Images
{
    private String header;
    private List<String> images;
}
