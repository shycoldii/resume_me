package ru.diplom.resume_builder.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.diplom.resume_builder.exception.AppException;
import ru.diplom.resume_builder.model.Role;
import ru.diplom.resume_builder.model.RoleEnum;
import ru.diplom.resume_builder.model.User;
import ru.diplom.resume_builder.payload.ApiResponse;
import ru.diplom.resume_builder.payload.JwtAuthenticationResponse;
import ru.diplom.resume_builder.payload.LoginRequest;
import ru.diplom.resume_builder.payload.SignUpRequest;
import ru.diplom.resume_builder.repository.RoleRepository;
import ru.diplom.resume_builder.repository.UserRepository;
import ru.diplom.resume_builder.security.JwtTokenProvider;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

import static ru.diplom.resume_builder.controller.ControllerApiPath.AUTH_PATH;

/**
 * Контроллер аутентификации.
 *
 * @author Darya Alexandrova
 * @since 2023.03.05
 */
@RestController
@RequestMapping(AUTH_PATH)
public class AuthController {

    /**
     * Менеджер аутентификаций.
     */
    private final AuthenticationManager authenticationManagerBean;

    /**
     * Репозиторий пользователей.
     */
    private final UserRepository userRepository;

    /**
     * Репозиторий ролей.
     */
    private final RoleRepository roleRepository;

    /**
     * Энкодер паролей.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Провайдер токенов.
     */
    private final JwtTokenProvider tokenProvider;

    /**
     * Конструктор.
     *
     * @param authenticationManagerBean менеджер аутентификации
     * @param userRepository            репозиторий пользователей
     * @param roleRepository            репозиторий ролей
     * @param passwordEncoder           энкодер паролей
     * @param tokenProvider             провайдер токенов
     */
    public AuthController(AuthenticationManager authenticationManagerBean, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.authenticationManagerBean = authenticationManagerBean;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }


    /**
     * Запрос на аутентификацию пользователя.
     *
     * @param loginRequest запрос на аутентификацию
     * @return ответ
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManagerBean.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    /**
     * Запрос на регистрацию пользователя.
     *
     * @param signUpRequest запрос на регистрацию
     * @return ответ
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(false, "Email Address is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));
        user.setRoles(Collections.singleton(userRole));
        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    /**
     * Проверяет, свободно ли пользовательское имя.
     *
     * @param username имя
     * @return свободно/не свободно
     */
    @GetMapping("/checkUsername")
    public ResponseEntity<Boolean> checkUsername(@RequestParam String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return ResponseEntity.ok(isAvailable);
    }

    /**
     * Проверяет, свободна ли пользовательская почта.
     *
     * @param email почта
     * @return свободна/не свободна
     */
    @GetMapping("/checkEmail")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return ResponseEntity.ok(isAvailable);
    }

}
