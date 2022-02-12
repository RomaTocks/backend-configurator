package com.tocks.backend.model.product;

import com.tocks.backend.model.product.additional.RamAdditionalInformation;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ram")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Ram extends Product
{
    private RamAdditionalInformation additional;
}
