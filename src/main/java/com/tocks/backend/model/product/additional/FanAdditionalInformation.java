package com.tocks.backend.model.product.additional;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FanAdditionalInformation extends Additional {
    String coolingType;
    String color;
    String socket;
    String dispelPower;
    String material;
    String heatPipes;
    String diameterFan;
    String maxSpeed;
    String PWM;
    String connectType;
    String depth;

}
