package ru.vitrix.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.vitrix.entity.Role;
import ru.vitrix.entity.UserEntity;
import ru.vitrix.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class AdminInitialisation {
    @Value("${ADMIN_USERNAME:admin}")
    private String adminUsername;
    @Value("${ADMIN_PASSWORD:admin}")
    private String adminPassword;

    @Autowired
    void initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        UserEntity admin = new UserEntity();
        admin.setUsername(adminUsername);
        admin.setPassword(passwordEncoder.encode(adminPassword));
        admin.setRole(Role.ADMIN);
        userRepository.save(admin);
    }
}
