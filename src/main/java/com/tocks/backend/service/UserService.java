package com.tocks.backend.service;

import com.tocks.backend.dto.configuration.AddConfigurationRequest;
import com.tocks.backend.dto.configuration.DeleteConfigurationRequest;
import com.tocks.backend.dto.favourite.FavouriteProductRequest;
import com.tocks.backend.dto.register.RegisterRequest;
import com.tocks.backend.model.user.User;
import org.springframework.http.ResponseEntity;

public interface UserService
{
    User findByLogin(String login);
    ResponseEntity<?> findByLoginAndPassword(String login, String password);
    ResponseEntity<?> saveUser(RegisterRequest registerRequest);
    ResponseEntity<?> addConfiguration(AddConfigurationRequest configurationRequest, String authorization);
    ResponseEntity<?> addFavouriteProduct(FavouriteProductRequest addFavouriteRequest, String authorization);
    ResponseEntity<?> deleteFavouriteProduct(FavouriteProductRequest deleteFavouriteRequest, String authorization);
    ResponseEntity<?> deleteConfiguration(DeleteConfigurationRequest configurationRequest, String authorization);
    ResponseEntity<?> getUserInformation(String authorization);
}
