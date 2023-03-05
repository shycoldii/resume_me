package ru.diplom.resume_builder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Главный класс приложения.
 *
 * @author Darya Alexandrova
 * @since 2023.02.25
 */
@SpringBootApplication
@EnableJpaAuditing
public class ResumeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResumeApplication.class, args);
    }
}
