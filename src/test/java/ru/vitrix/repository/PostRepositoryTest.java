package ru.vitrix.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Transactional
@Sql("/sql/posts.sql")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostRepositoryTest {
    @Autowired
    private PostRepository repository;

    @Test
    void findAllByTitle_ReturnsPageOfPostsWithoutLockedAccounts() {
        // given
        var filter = "%titlE%";
        var page = PageRequest.of(0, 5);

        // when
        var productsList = this.repository.findAllByTitle(filter, page).getContent();

        // then
        assertEquals(3, productsList.size());
    }

    @Test
    void findAll_ReturnsPageOfPostsWithoutLockedAccounts() {
        // given
        var page = PageRequest.of(0, 5);

        // when
        var productsList = this.repository.findAll(page).getContent();

        // then
        assertEquals(4, productsList.size());
    }
}