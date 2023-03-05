package ru.diplom.resume_builder.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Модель запроса регистрации.
 *
 * @author Darya Alexandrova
 * @since 2023.03.05
 */
@Getter
@Setter
public class SignUpRequest {

    /**
     * Имя.
     */
    @NotBlank
    @Size(min = 4, max = 40)
    private String name;

    /**
     * Пользовательское имя.
     */
    @NotBlank
    @Size(min = 3, max = 15)
    private String username;

    /**
     * Почта.
     */
    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    /**
     * Пароль.
     */
    @NotBlank
    @Size(min = 6, max = 20)
    private String password;
}
