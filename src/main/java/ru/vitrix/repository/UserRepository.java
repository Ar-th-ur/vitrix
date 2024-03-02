package ru.vitrix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vitrix.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsername(String username);
}
