package ru.vitrix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.vitrix.entity.PostEntity;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    @Query("select p from PostEntity p where p.title ilike :filter and p.owner.isAccountLocked = false")
    List<PostEntity> findAllByTitle(@Param("filter") String filter);

    @Override
    @Query("select p from PostEntity p where p.owner.isAccountLocked = false")
    List<PostEntity> findAll();
}
