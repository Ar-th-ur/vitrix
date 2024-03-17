package ru.vitrix.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.vitrix.entity.PostEntity;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    @Query("select p from PostEntity p where p.title ilike :filter and p.owner.isAccountLocked = false")
    Page<PostEntity> findAllByTitle(@Param("filter") String filter, Pageable pageable);

    @Override
    @Query("select p from PostEntity p where p.owner.isAccountLocked = false")
    Page<PostEntity> findAll(Pageable pageable);
}
