package ru.diplom.resume_builder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Базовая модель приложения.
 *
 * @author Darya Alexandrova
 * @since 2023.03.03
 */
@MappedSuperclass
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractModel implements Serializable {

    /**
     * Дата создания сущности.
     */
    @CreatedDate
    @JsonIgnore
    private LocalDateTime createdAt;

    /**
     * Дата последней модификации сущности.
     */
    @LastModifiedDate
    @JsonIgnore
    private LocalDateTime lastModifiedAt;

    /**
     * Идентификатор сущности.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

}
