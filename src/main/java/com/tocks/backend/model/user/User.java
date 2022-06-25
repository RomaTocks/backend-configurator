package com.tocks.backend.model.user;

import com.tocks.backend.model.configuration.PreparedConfiguration;
import com.tocks.backend.model.product.FavouriteProducts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User
{
    @Id
    private String id;
    private String login;
    private String mail;
    private String password;
    private String role;
    private List<PreparedConfiguration> configurations;
    private FavouriteProducts favouriteProducts;
}
