package ru.diplom.resume_builder.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, если произошло что-то на клиенте.
 *
 * @author Darya Alexandrova
 * @since 2023.03.05
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    /**
     * Конструктор.
     *
     * @param message сообщение
     */
    public BadRequestException(String message) {
        super(message);
    }

    /**
     * Конструктор.
     *
     * @param message сообщение
     * @param cause   причина
     */
    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
