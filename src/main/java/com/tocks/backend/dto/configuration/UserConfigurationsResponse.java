package com.tocks.backend.dto.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserConfigurationsResponse
{
    private List<PreparedConfigurationResponse> configurations;
}
