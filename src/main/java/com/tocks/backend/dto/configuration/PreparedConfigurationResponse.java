package com.tocks.backend.dto.configuration;

import com.tocks.backend.model.configuration.ConfigurationPositions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreparedConfigurationResponse extends CheckedConfigurationResponse
{
    private String id;
    private String name;
    private ConfigurationPositions positions;
}
