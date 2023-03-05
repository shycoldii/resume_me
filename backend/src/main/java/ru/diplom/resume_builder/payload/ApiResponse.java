package ru.diplom.resume_builder.payload;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Модель ответа.
 *
 * @author Darya Alexandrova
 * @since 2023.03.05
 */
@Getter
@Setter
@RequiredArgsConstructor
public class ApiResponse {

    /**
     * Запрос успешен/нет.
     */
    @NonNull
    private Boolean success;

    /**
     * Сообщение.
     */
    @NonNull
    private String message;
}
