package com.tocks.backend.model.product;


import com.tocks.backend.model.product.additional.GraphicCardAdditionalInformation;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "graphic-cards")
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class GraphicCard extends Product
{
    private GraphicCardAdditionalInformation additional;
}
