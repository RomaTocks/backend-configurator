package com.tocks.backend.model.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tocks.backend.model.common.additional.Images;
import com.tocks.backend.model.common.additional.Manufacturer;
import com.tocks.backend.model.common.additional.Rating;
import com.tocks.backend.model.common.sellers.Position;
import com.tocks.backend.model.common.sellers.Prices;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.lang.reflect.Field;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Product {
    @Id
    protected String id;
    protected String key;
    protected String name;
    protected String extended_name;
    protected Images images;
    protected Rating reviews;
    protected Manufacturer manufacturer;
    protected String description;
    protected Prices prices;
    protected String html_url;
    protected List<Position> positions;
    private Integer total;

    public Object getFieldValue(String name) {
        Field field = null;
        try
        {
            field = this.getClass().getDeclaredField(name);
            return field.get(this);
        }
        catch (NoSuchFieldException | IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
