package com.tocks.backend.model.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tocks.backend.model.common.Manufacturer;
import com.tocks.backend.model.common.Prices;
import com.tocks.backend.model.common.sellers.Position;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Product {
    @Id
    protected String id;
    protected String key;
    protected String name;
    protected String description;
    protected Manufacturer manufacturer;
    protected Prices prices;
    protected String html_url;
    protected List<Position> positions;
    private Integer total;
}
