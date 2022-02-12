package com.tocks.backend.model.product;

import com.tocks.backend.model.product.additional.CPUAdditionalInformation;
import com.tocks.backend.model.product.additional.values.CPUAdditionalValues;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "processors")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class CPU extends Product
{
    private CPUAdditionalInformation additional;
    private CPUAdditionalValues values;
}
