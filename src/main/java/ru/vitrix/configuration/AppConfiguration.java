package ru.vitrix.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.vitrix.entity.Role;
import ru.vitrix.entity.UserEntity;
import ru.vitrix.repository.UserRepository;

import java.util.Locale;

@Configuration
public class AppConfiguration {

    @Bean
    public MessageSource messageSource() {
        var messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setDefaultLocale(Locale.US);
        messageSource.setBasename("classpath:messages");
        messageSource.setCacheSeconds(3600);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    CommandLineRunner setAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return (args) -> {
            var username = System.getenv("ADMIN_USERNAME");
            var password = System.getenv("ADMIN_PASSWORD");

            var adminOpt = userRepository.findByUsername(username);
            if (adminOpt.isPresent()) {
                var admin = adminOpt.get();
                admin.setUsername(username);
                admin.setPassword(passwordEncoder.encode(password));
                userRepository.save(admin);
                return;
            }

            var admin = new UserEntity();
            admin.setUsername(username);
            admin.setPassword(passwordEncoder.encode(password));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
        };
    }
}
