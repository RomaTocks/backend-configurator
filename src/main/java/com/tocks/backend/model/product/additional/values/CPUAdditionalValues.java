package com.tocks.backend.model.product.additional.values;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CPUAdditionalValues extends Values
{
    Integer birthday;
    Integer cores;
    Integer streams;
    Double baseFrequency;
    Double turboFrequency;
    Integer cpuRamChannel;
    Integer ramFrequency;
    Integer tdp;
}
