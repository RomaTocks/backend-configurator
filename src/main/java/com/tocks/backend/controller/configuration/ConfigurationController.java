package com.tocks.backend.controller.configuration;

import com.tocks.backend.dto.configuration.CheckedConfigurationResponse;
import com.tocks.backend.dto.configuration.UserConfigurationsRequest;
import com.tocks.backend.dto.configuration.UserConfigurationsResponse;
import com.tocks.backend.dto.favourite.FavouriteProductsResponse;
import com.tocks.backend.model.configuration.Configuration;
import com.tocks.backend.model.product.FavouriteProducts;
import com.tocks.backend.service.implementation.ConfigurationServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
public class ConfigurationController
{
    private final ConfigurationServiceImpl service;

    public ConfigurationController(ConfigurationServiceImpl service)
    {
        this.service = service;
    }

    @PostMapping("/config")
    @CrossOrigin(origins = "*")
    public ResponseEntity<CheckedConfigurationResponse> configureComponentsOfPersonalComputer(@RequestBody Configuration configuration) {
        return service.checkConfiguration(configuration);
    }
    @GetMapping("api/user/favourite")
    public FavouriteProductsResponse getFavouriteProducts(@RequestBody FavouriteProducts favouriteProducts) {
        return service.findAllFavouriteProducts(favouriteProducts);
    }
    @GetMapping("/api/user/configuration")
    public ResponseEntity<UserConfigurationsResponse> getUserConfigurations(@RequestBody UserConfigurationsRequest userConfigurationsRequest) {
        return service.getUserConfigurations(userConfigurationsRequest);
    }
}
