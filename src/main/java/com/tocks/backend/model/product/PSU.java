package com.tocks.backend.model.product;

import com.tocks.backend.model.product.additional.PSUAdditionalInformation;
import com.tocks.backend.model.product.additional.values.PSUAdditionalValues;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "psu")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PSU extends Product
{
    private PSUAdditionalInformation additional;
    private PSUAdditionalValues values;
}
