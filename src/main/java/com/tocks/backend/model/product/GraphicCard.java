package com.tocks.backend.model.product;


import com.tocks.backend.model.product.additional.GraphicCardAdditionalInformation;
import com.tocks.backend.model.product.additional.values.GraphicCardAdditionalValues;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "graphic-cards")
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@ToString(callSuper = true)
public class GraphicCard extends Product
{
    private GraphicCardAdditionalInformation additional;
    private GraphicCardAdditionalValues values;
}
