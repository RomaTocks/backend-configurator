package com.tocks.backend.model.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreparedConfiguration
{
    private String id;
    private String name;
    private Configuration configuration;
    private ConfigurationPositions positions;
}
