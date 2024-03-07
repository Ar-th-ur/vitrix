package ru.vitrix;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.vitrix.entity.Role;
import ru.vitrix.entity.UserEntity;
import ru.vitrix.repository.UserRepository;

@EnableJpaAuditing
@SpringBootApplication
public class VitrixApplication {

    public static void main(String[] args) {
        SpringApplication.run(VitrixApplication.class, args);
    }

    @Bean
    CommandLineRunner setAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return (args) -> {
            String username = System.getenv("ADMIN_USERNAME");
            String password = System.getenv("ADMIN_PASSWORD");

            var adminOpt = userRepository.findByUsername(username);
            if (adminOpt.isPresent()) {
                var admin = adminOpt.get();
                admin.setUsername(username);
                admin.setPassword(password);
                userRepository.save(admin);

            }

            var admin = new UserEntity();
            admin.setUsername(username);
            admin.setPassword(passwordEncoder.encode(password));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
        };
    }
}
