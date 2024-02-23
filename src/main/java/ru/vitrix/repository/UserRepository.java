package ru.vitrix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vitrix.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsername(String username);
}