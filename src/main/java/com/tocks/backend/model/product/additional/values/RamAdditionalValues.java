package com.tocks.backend.model.product.additional.values;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RamAdditionalValues extends Values
{
    Integer kit;
    Integer value;
    Integer singleValue;
    Integer frequency;
}
