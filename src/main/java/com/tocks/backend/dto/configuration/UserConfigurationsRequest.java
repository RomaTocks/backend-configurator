package com.tocks.backend.dto.configuration;

import com.tocks.backend.model.configuration.PreparedConfiguration;
import lombok.Data;

import java.util.List;

@Data
public class UserConfigurationsRequest
{
    private List<PreparedConfiguration> configurations;
}
