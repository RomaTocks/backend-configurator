package com.tocks.backend.repository.common;

import com.tocks.backend.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String>
{
    User findByLogin(String login);
    Optional<User> findByLoginAndPassword(String login, String password);
    boolean existsByLoginOrMail(String login, String mail);
}
