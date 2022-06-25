package com.tocks.backend.service.implementation;

import com.tocks.backend.configuration.jwt.JwtProvider;
import com.tocks.backend.dto.auth.AuthRequest;
import com.tocks.backend.dto.auth.AuthResponse;
import com.tocks.backend.dto.configuration.AddConfigurationRequest;
import com.tocks.backend.dto.configuration.DeleteConfigurationRequest;
import com.tocks.backend.dto.favourite.FavouriteProductRequest;
import com.tocks.backend.dto.register.RegisterRequest;
import com.tocks.backend.dto.user.UserResponse;
import com.tocks.backend.model.configuration.PreparedConfiguration;
import com.tocks.backend.model.product.FavouriteProducts;
import com.tocks.backend.model.user.User;
import com.tocks.backend.repository.common.UserRepository;
import com.tocks.backend.response.exception.MessageResponse;
import com.tocks.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfigurationServiceImpl service;
    private final JwtProvider jwtProvider;

    public UserServiceImpl(UserRepository userRepository, ConfigurationServiceImpl service, PasswordEncoder passwordEncoder, JwtProvider jwtProvider)
    {
        this.userRepository = userRepository;
        this.service = service;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public User findByLogin(String login)
    {
        return userRepository.findByLogin(login);
    }

    @Override
    public ResponseEntity<?> findByLoginAndPassword(String login, String password)
    {
        User user = findByLogin(login);
        if(user != null) {
            if(passwordEncoder.matches(password, user.getPassword())) {
                String token = jwtProvider.generateToken(user.getLogin());
                return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new MessageResponse("Неверные данные"), HttpStatus.BAD_REQUEST);

    }

    @Override
    public ResponseEntity<?> saveUser(RegisterRequest registerRequest)
    {
        if(userRepository.existsByLoginOrMail(registerRequest.getLogin(), registerRequest.getMail()))
        {
            return new ResponseEntity<>(new MessageResponse("Пользователь с такими данными уже существует."), HttpStatus.BAD_REQUEST);
        }
        else {
            User user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setLogin(registerRequest.getLogin());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setMail(registerRequest.getMail());
            user.setRole("ROLE_USER");
            user.setConfigurations(new ArrayList<>());
            FavouriteProducts favouriteProducts = new FavouriteProducts(new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
            user.setFavouriteProducts(favouriteProducts);
            user = userRepository.save(user);
            AuthRequest authRequest = new AuthRequest(user.getLogin(), user.getPassword());
            return new ResponseEntity<>(authRequest, HttpStatus.ACCEPTED);
        }
    }

    @Override
    public ResponseEntity<?> addConfiguration(AddConfigurationRequest configurationRequest, String authorization)
    {
        String token = jwtProvider.getToken(authorization);
        String login = jwtProvider.getLoginFromToken(token);
        User user = findByLogin(login);
        if(user != null) {
            List<PreparedConfiguration> configurations = user.getConfigurations();
            PreparedConfiguration preparedConfiguration = configurationRequest.getPreparedConfiguration();
            preparedConfiguration.setId(UUID.randomUUID().toString());
            configurations.add(preparedConfiguration);
            user.setConfigurations(configurations);
            userRepository.save(user);
            return new ResponseEntity<>(new MessageResponse("Сборка сохранена успешно!"), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(new MessageResponse("Невозможно сохранить сборку, попробуйте обновить страницу!"), HttpStatus.UNAUTHORIZED);
    }

    @Override
    public  ResponseEntity<?> addFavouriteProduct(FavouriteProductRequest addFavouriteRequest, String authorization)
    {
        String section = addFavouriteRequest.getSection();
        String token = jwtProvider.getToken(authorization);
        String login = jwtProvider.getLoginFromToken(token);
        User user = userRepository.findByLogin(login);
        if(user != null) {
            FavouriteProducts favouriteProducts = user.getFavouriteProducts();
            actionWithFavourite(false, section, addFavouriteRequest.getId(), favouriteProducts);
            user.setFavouriteProducts(favouriteProducts);
            userRepository.save(user);
            return new ResponseEntity<>(new MessageResponse("Success"), HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<>(new MessageResponse("Unauthorized"), HttpStatus.UNAUTHORIZED);
        }

    }

    @Override
    public ResponseEntity<?> deleteFavouriteProduct(FavouriteProductRequest deleteFavouriteRequest, String authorization)
    {
        String section = deleteFavouriteRequest.getSection();
        String token = jwtProvider.getToken(authorization);
        String login = jwtProvider.getLoginFromToken(token);
        User user = userRepository.findByLogin(login);
        if(user != null) {
            FavouriteProducts favouriteProducts = user.getFavouriteProducts();
            actionWithFavourite(true, section, deleteFavouriteRequest.getId(), favouriteProducts);
            user.setFavouriteProducts(favouriteProducts);
            userRepository.save(user);
            return new ResponseEntity<>(new MessageResponse("Success"), HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<>(new MessageResponse("Unauthorized"), HttpStatus.UNAUTHORIZED);
        }
    }

    private void actionWithFavourite(boolean delete, String section, String id, FavouriteProducts favouriteProducts) {
        switch (section) {
            case "cpu" : {
                List<String> cpuFavourite = favouriteProducts.getCpu();
                addOrDeleteFavouriteProduct(delete, id, cpuFavourite);
                favouriteProducts.setCpu(cpuFavourite);
                break;
            }
            case "gpu" : {
                List<String> gpuFavourite = favouriteProducts.getGraphicCard();
                addOrDeleteFavouriteProduct(delete, id, gpuFavourite);
                favouriteProducts.setGraphicCard(gpuFavourite);
                break;
            }
            case "psu" : {
                List<String> psuFavourite = favouriteProducts.getPsu();
                addOrDeleteFavouriteProduct(delete, id, psuFavourite);
                favouriteProducts.setPsu(psuFavourite);
                break;
            }
            case "motherboard" : {
                List<String> motherboardFavourite = favouriteProducts.getMotherboard();
                addOrDeleteFavouriteProduct(delete, id, motherboardFavourite);
                favouriteProducts.setMotherboard(motherboardFavourite);
                break;
            }
            case "fan" : {
                List<String> fanFavourite = favouriteProducts.getFan();
                addOrDeleteFavouriteProduct(delete, id, fanFavourite);
                favouriteProducts.setFan(fanFavourite);
                break;
            }
            case "ram" : {
                List<String> ramFavourite = favouriteProducts.getRam();
                addOrDeleteFavouriteProduct(delete, id, ramFavourite);
                favouriteProducts.setRam(ramFavourite);
                break;
            }
            case "chassis" : {
                List<String> chassisFavourite = favouriteProducts.getChassis();
                addOrDeleteFavouriteProduct(delete, id, chassisFavourite);
                favouriteProducts.setChassis(chassisFavourite);
                break;
            }
            default: {

            }
        }
    }

    private void addOrDeleteFavouriteProduct(boolean delete, String id, List<String> favouriteProductsList)
    {
        if (delete) {
            favouriteProductsList.removeIf(favouriteId -> favouriteId.equals(id));
        }
        else {
            if(!favouriteProductsList.contains(id)) {
                favouriteProductsList.add(id);
            }
        }
    }

    @Override
    public ResponseEntity<?> deleteConfiguration(DeleteConfigurationRequest deleteConfigurationRequest, String authorization)
    {
        String token = jwtProvider.getToken(authorization);
        String login = jwtProvider.getLoginFromToken(token);
        User user = findByLogin(login);
        if(user != null) {
            List<PreparedConfiguration> configurations = user.getConfigurations();
            configurations.removeIf(configuration -> Objects.equals(configuration.getId(), deleteConfigurationRequest.getConfigurationId()));
            user.setConfigurations(configurations);
            userRepository.save(user);
            return new ResponseEntity<>(new MessageResponse("Success"), HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<>(new MessageResponse("Unauthorized"), HttpStatus.UNAUTHORIZED);
        }

    }

    @Override
    public ResponseEntity<?> getUserInformation(String authorization)
    {
        String token = jwtProvider.getToken(authorization);
        String login = jwtProvider.getLoginFromToken(token);
        User user = findByLogin(login);
        if(user != null) {
            return new ResponseEntity<>(new UserResponse(user.getLogin(), user.getMail(), user.getConfigurations(), user.getFavouriteProducts()), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new MessageResponse("Unauthorized"), HttpStatus.UNAUTHORIZED);
        }
    }
}
