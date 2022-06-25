package com.tocks.backend.dto.user;

import com.tocks.backend.model.configuration.PreparedConfiguration;
import com.tocks.backend.model.product.FavouriteProducts;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserResponse
{
    private String login;
    private String mail;
    private List<PreparedConfiguration> configurations;
    private FavouriteProducts favouriteProducts;
}
