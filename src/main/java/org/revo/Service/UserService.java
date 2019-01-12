package org.revo.Service;

import org.revo.Domain.User;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Optional;

/**
 * Created by ashraf on 22/04/17.
 */
public interface UserService {
    Optional<String> current();

    Jwt cur(Jwt user);
}
