package com.tocks.backend.dto.configuration;

import com.tocks.backend.model.configuration.PreparedConfiguration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddConfigurationRequest
{
    private PreparedConfiguration preparedConfiguration;
}
