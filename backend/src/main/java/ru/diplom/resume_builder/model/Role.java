package ru.diplom.resume_builder.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import static ru.diplom.resume_builder.model.Role.TABLE_NAME;

/**
 * Роль.
 *
 * @author Darya Alexandrova
 * @since 2023.03.03
 */
@Entity
@Table(name = TABLE_NAME)
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
public class Role {

    /**
     * Наименование таблицы.
     */
    public static final String TABLE_NAME = "roles";

    /**
     * Идентификатор роли.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Наименование роли.
     */
    @Enumerated(EnumType.STRING)
    @NaturalId
    @NonNull
    @Column(length = 60)
    private RoleEnum name;

}
