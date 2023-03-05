package ru.diplom.resume_builder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.diplom.resume_builder.model.User;

import java.util.Optional;

/**
 * Репозиторий пользователей.
 *
 * @author Darya Alexandrova
 * @since 2023.03.03
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Ищет пользователя по почте.
     *
     * @param email почта
     * @return пользователь
     */
    Optional<User> findByEmail(String email);

    /**
     * Ищет пользователя по почте или имени.
     *
     * @param email    почта
     * @param username имя
     * @return пользователь
     */
    Optional<User> findByUsernameOrEmail(String username, String email);

    /**
     * Ищет пользователя по имени.
     *
     * @param username имя
     * @return пользователь
     */
    Optional<User> findByUsername(String username);

    /**
     * Существует ли пользователь.
     *
     * @param username имя
     * @return существует/не существует
     */
    Boolean existsByUsername(String username);

    /**
     * Существует ли пользователь.
     *
     * @param email почта
     * @return существует/не существует
     */
    Boolean existsByEmail(String email);

}
