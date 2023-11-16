package ua.ypon.finalsecurityapp.FirstSecurityApp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author ua.ypon 10.11.2023
 */
@Component
public class JWTUtil {

    @Value("${jwt-secret")
    private String secret;//впроваджуємо наш секрет із зовнішнього файлу у application-properties

    public String generateToken(String username) {
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());//строк дійсності токена(toInstant-перевод в timestamp)

        return JWT.create()//jwt токен для отправки клієнту
                .withSubject("User details")//дані саме цього користувача
                .withClaim("username", username)//їх може бути скілбки завгодно
                .withIssuedAt(new Date())//установка часу
                .withIssuer("ypon")//хто видав
                .withExpiresAt(expirationDate)//час дійсності
                .sign(Algorithm.HMAC256(secret));//секрет
    }

    public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {//валідація токена для розбору-хто звернувся?
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))//перевірка нашого токену
                .withSubject("User details")
                .withIssuer("ypon")
                .build();

        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getClaim("username").asString();
    }
}
