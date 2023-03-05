package ru.diplom.resume_builder.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.diplom.resume_builder.model.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация предоставления информации о пользователях.
 * Spring Security будет использовать этот объект для того, чтобы проводить аутентификацию и авторизацию.
 *
 * @author Darya Alexandrova
 * @since 2023.03.05
 */
public class UserDetailsImpl implements UserDetails {

    /**
     * Идентификатор пользователя.
     */
    private final Long id;

    /**
     * Имя пользователя.
     */
    private final String name;

    /**
     * Пользовательское имя.
     */
    private final String username;

    /**
     * Электронная почта.
     */
    @JsonIgnore
    private final String email;

    /**
     * Пароль.
     */
    @JsonIgnore
    private final String password;

    /**
     * Список прав.
     */
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * Конструктор.
     *
     * @param user        пользователь
     * @param authorities права
     */
    public UserDetailsImpl(final User user, final Collection<? extends GrantedAuthority> authorities) {
        this.id = user.getId();
        this.name = user.getName();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.authorities = authorities;
    }

    /**
     * Создает экземпляр класса на основе пользователя.
     *
     * @param user пользователь
     * @return экземпляр класса
     */
    public static UserDetailsImpl create(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName().name())
        ).collect(Collectors.toList());

        return new UserDetailsImpl(
                user,
                authorities
        );
    }

    /**
     * Получает идентификатор пользователя.
     *
     * @return идентификатор
     */
    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

