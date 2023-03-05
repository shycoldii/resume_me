package ru.diplom.resume_builder.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diplom.resume_builder.model.User;
import ru.diplom.resume_builder.repository.UserRepository;
import ru.diplom.resume_builder.security.UserDetailsImpl;

/**
 * Реализация сервиса пользовательской информации.
 *
 * @author Darya Alexandrova
 * @since 2023.03.05
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * Репозиторий пользователей.
     */
    private final UserRepository userRepository;

    /**
     * Конструктор.
     *
     * @param userRepository репозиторий пользователей
     */
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail)
                );

        return UserDetailsImpl.create(user);
    }


    /**
     * Метод загрузки пользовательской информации по идентификатору.
     * Используется JWTAuthenticationFilter-ом.
     *
     * @param id идентификатор
     * @return пользовательская информация
     */
    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return UserDetailsImpl.create(user);
    }
}
