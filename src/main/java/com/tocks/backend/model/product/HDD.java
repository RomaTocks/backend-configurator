package com.tocks.backend.model.product;

import com.tocks.backend.model.product.additional.HDDAdditionalInformation;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "hdd")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class HDD extends Product
{
    private HDDAdditionalInformation additional;
}
