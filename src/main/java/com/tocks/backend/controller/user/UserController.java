package com.tocks.backend.controller.user;

import com.tocks.backend.dto.configuration.AddConfigurationRequest;
import com.tocks.backend.dto.configuration.DeleteConfigurationRequest;
import com.tocks.backend.dto.favourite.FavouriteProductRequest;
import com.tocks.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController
{
    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> userInformation(@RequestHeader("Authorization") String authorization) {
        return userService.getUserInformation(authorization);
    }
    @PostMapping("/configuration")
    public ResponseEntity<?> addConfiguration(@RequestHeader("Authorization") String authorization, @RequestBody AddConfigurationRequest configurationRequest) {
        return userService.addConfiguration(configurationRequest, authorization);
    }
    @DeleteMapping("/configuration")
    public ResponseEntity<?> deleteConfiguration( @RequestHeader("Authorization") String authorization, @RequestBody DeleteConfigurationRequest deleteConfigurationRequest) {
        return userService.deleteConfiguration(deleteConfigurationRequest, authorization);
    }
    @PostMapping("/favourite")
    public ResponseEntity<?> addFavouriteProduct(@RequestHeader("Authorization") String authorization, @RequestBody FavouriteProductRequest favouriteRequest) {
        return userService.addFavouriteProduct(favouriteRequest, authorization);
    }
    @DeleteMapping("/favourite")
    public ResponseEntity<?> deleteFavouriteProduct(@RequestHeader("Authorization") String authorization, @RequestBody FavouriteProductRequest favouriteProductRequest) {
        return userService.deleteFavouriteProduct(favouriteProductRequest, authorization);
    }
}
