package com.tocks.backend.model.configuration;

import com.tocks.backend.model.product.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Configuration
{
    private CPU cpu;
    private Motherboard motherboard;
    private Fan fan;
    private HDD hdd;
    private PSU psu;
    private Ram ram;
    private SSD ssd;
    private GraphicCard graphicCard;
    private Chassis chassis;
}
