package ru.diplom.resume_builder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static ru.diplom.resume_builder.model.User.TABLE_NAME;

/**
 * Пользователь системы.
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
public class User extends AbstractModel {

    /**
     * Наименование таблицы.
     */
    public static final String TABLE_NAME = "users";

    /**
     * Имя пользователя.
     */
    @NotBlank
    @NonNull
    @Size(max = 40)
    private String name;

    /**
     * Пользовательское имя.
     */
    @NotBlank
    @NonNull
    @NaturalId
    @Size(max = 15)
    @Column(unique = true)
    private String username;

    /**
     * Почта.
     */
    @NaturalId
    @NotBlank
    @NonNull
    @Size(max = 40)
    @Email
    @Column(unique = true)
    @JsonIgnore
    private String email;

    /**
     * Пароль.
     */
    @NotBlank
    @NonNull
    @Size(max = 100)
    @JsonIgnore
    private String password;

    /**
     * Роли.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_to_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username) ||
                email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
