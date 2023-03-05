package ru.diplom.resume_builder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.diplom.resume_builder.model.Role;
import ru.diplom.resume_builder.model.RoleEnum;

import java.util.Optional;

/**
 * Репозиторий ролей.
 *
 * @author Darya Alexandrova
 * @since 2023.03.03
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Ищет роль по имени.
     *
     * @param roleEnum имя
     * @return роль
     */
    Optional<Role> findByName(RoleEnum roleEnum);

}
