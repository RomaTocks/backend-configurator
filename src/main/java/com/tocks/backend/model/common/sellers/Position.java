package com.tocks.backend.model.common.sellers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Position {
    private String id;
    private String article;
    private Integer shop_id;
    private Price position_price;
    private String importer;
    private String html_url;
    private String title;
    private String logo;
}
