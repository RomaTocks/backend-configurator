package com.tocks.backend.model.product;

import com.tocks.backend.model.product.additional.SSDAdditionalInformation;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ram")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SSD extends Product
{
    private SSDAdditionalInformation additional;
}
