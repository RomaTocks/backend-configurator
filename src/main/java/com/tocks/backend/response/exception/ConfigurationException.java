package com.tocks.backend.response.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigurationException
{
    protected String type;
    protected String targetProductName;
    protected String exceptionProductName;
    protected String message;

    public ConfigurationException(String type, String exceptionProductName, String message)
    {
        this.type = type;
        this.exceptionProductName = exceptionProductName;
        this.message = message;
    }
}

