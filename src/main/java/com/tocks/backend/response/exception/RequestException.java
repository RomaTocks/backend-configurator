package com.tocks.backend.response.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RequestException
{
    private String message;
}
