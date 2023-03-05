package ru.diplom.resume_builder.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Класс для создания JWT после успешного входа пользователя и проверки JWT, отправленного в заголовке авторизации запросов.
 *
 * @author Darya Alexandrova
 * @since 2023.03.05
 */
@Component
public class JwtTokenProvider {

    /**
     * Логгер.
     */
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    /**
     * JWT-секрет.
     */
    @Value("${app.jwtSecret}")
    private String jwtSecret;

    /**
     * Количество МС действия токена.
     */
    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    /**
     * Генерирует токен.
     *
     * @param authentication аутентификация
     * @return токен
     */
    public String generateToken(Authentication authentication) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(Long.toString(userDetails.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * Получает идентификатор пользователя по токену.
     *
     * @param token токен
     * @return идентификатор пользователя
     */
    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    /**
     * Определяет валидность токена.
     *
     * @param authToken токен
     * @return валиден ли токен
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty");
        }
        return false;
    }

}
