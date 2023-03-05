package ru.diplom.resume_builder.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, если запрашиваемый ресурс не найден.
 *
 * @author Darya Alexandrova
 * @since 2023.03.05
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Имя ресурса.
     */
    private final String resourceName;

    /**
     * Имя поля.
     */
    private final String fieldName;

    /**
     * Значение поля.
     */
    private final Object fieldValue;

    /**
     * Конструктор.
     *
     * @param resourceName имя ресурса
     * @param fieldName    имя поля
     * @param fieldValue   значение поля
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

}
