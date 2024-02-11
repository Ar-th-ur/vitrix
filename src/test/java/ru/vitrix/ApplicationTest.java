package ru.vitrix;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApplicationTest {

    @Test
    void configure() {
        Assertions.assertThat(true).isTrue();
    }
}
