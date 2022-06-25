package com.tocks.backend.dto.configuration;

import com.tocks.backend.model.configuration.Configuration;
import com.tocks.backend.response.exception.Errors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckedConfigurationResponse
{
    private Errors error;
    private Configuration configuration;
}
