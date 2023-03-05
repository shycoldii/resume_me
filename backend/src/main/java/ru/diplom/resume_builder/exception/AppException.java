package ru.diplom.resume_builder.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, если произошло что-то на сервере.
 *
 * @author Darya Alexandrova
 * @since 2023.03.05
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AppException extends RuntimeException {

    /**
     * Конструктор.
     *
     * @param message сообщение
     */
    public AppException(String message) {
        super(message);
    }

    /**
     * Конструктор.
     *
     * @param message сообщение
     * @param cause   причина
     */
    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}
