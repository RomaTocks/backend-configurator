package com.tocks.backend.controller.configuration;

import com.tocks.backend.model.configuration.Configuration;
import com.tocks.backend.service.implementation.ConfigurationServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/config")
public class ConfigurationController
{
    private final ConfigurationServiceImpl service;

    public ConfigurationController(ConfigurationServiceImpl service)
    {
        this.service = service;
    }

    @PostMapping()
    @CrossOrigin(origins = "*")
    public ResponseEntity<Map<String,Object>> config(@RequestBody Configuration configuration) {
        System.out.println(configuration);
        return service.checkConfiguration(configuration);
    }
}
