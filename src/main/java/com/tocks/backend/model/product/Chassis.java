package com.tocks.backend.model.product;

import com.tocks.backend.model.product.additional.ChassisAdditionalInformation;
import com.tocks.backend.model.product.additional.values.ChassisAdditionalValues;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "chassis")
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Chassis extends Product
{
    private ChassisAdditionalInformation additional;
    private ChassisAdditionalValues values;
}
