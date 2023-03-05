package ru.diplom.resume_builder.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Модель запроса логина.
 *
 * @author Darya Alexandrova
 * @since 2023.03.05
 */
@Getter
@Setter
public class LoginRequest {

    /**
     * Пользовательское имя или почта.
     * Позволяет вводить пользователю любой вариант.
     */
    @NotBlank
    private String usernameOrEmail;

    /**
     * Пароль.
     */
    @NotBlank
    private String password;
}
