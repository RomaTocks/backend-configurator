package com.tocks.backend.model.product;

import com.tocks.backend.model.product.additional.FanAdditionalInformation;
import com.tocks.backend.model.product.additional.values.FanAdditionalValues;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "fan")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Fan extends Product
{
    private FanAdditionalInformation additional;
    private FanAdditionalValues values;
}
