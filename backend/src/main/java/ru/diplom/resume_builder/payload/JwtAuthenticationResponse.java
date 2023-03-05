package ru.diplom.resume_builder.payload;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Ответ аутентификации JWT.
 *
 * @author Darya Alexandrova
 * @since 2023.03.05
 */
@Getter
@Setter
@RequiredArgsConstructor
public class JwtAuthenticationResponse {

    /**
     * Токен.
     */
    @NonNull
    private String accessToken;

    /**
     * Тип токена.
     */
    private String tokenType = "Bearer";
}
