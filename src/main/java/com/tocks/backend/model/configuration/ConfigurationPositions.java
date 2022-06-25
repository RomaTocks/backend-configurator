package com.tocks.backend.model.configuration;

import com.tocks.backend.model.common.sellers.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationPositions
{
    private Position cpu;
    private Position motherboard;
    private Position fan;
    private Position hdd;
    private Position psu;
    private Position ram;
    private Position ssd;
    private Position graphicCard;
    private Position chassis;
}
