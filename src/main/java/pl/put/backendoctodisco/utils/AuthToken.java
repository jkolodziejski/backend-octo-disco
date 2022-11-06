package pl.put.backendoctodisco.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import pl.put.backendoctodisco.entity.User;
import pl.put.backendoctodisco.repository.UserRepository;
import pl.put.backendoctodisco.service.UserService;
import springfox.documentation.spring.web.json.Json;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;


public class AuthToken {

    private final static int SECOND = 1000;
    private final static int EXPIRATION_TIME_SEC = 3 * 60 * 60 * SECOND;


    private String token;

    public AuthToken(String token){
        this.token = token;
    }

    public AuthToken(User user) {
        long now = System.currentTimeMillis();
        SecretKey key;
        try {
            String unhashedKey = Files.readAllLines(Paths.get("authorization.key")).get(0);
            key = Keys.hmacShaKeyFor(unhashedKey.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Date expirationDate = new Date(now + EXPIRATION_TIME_SEC);

        HashMap<String, Object> map = new HashMap<>(1);
        map.put("typ", "JWT");

        this.token = Jwts.builder()
                .setSubject(user.getLogin())
                .claim("roles", "user")
                .setIssuedAt(new Date(now))
                .setExpiration(expirationDate)
                .setHeader(map)
                .signWith(key)
                .compact();
    }

    public State checkToken() {
        if(token.isEmpty()){
            return State.EXPIRED;
        }
        return State.ACTIVE_USER;
    }

    @Override
    public String toString() {
        return token;
    }

    public String getHeaders(){
        return encode(0);
    }

    public String getPayload(){
        return encode(1);
    }

    public String getSignature(){
        return encode(2);
    }
    private String encode(int part){
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String src = this.token;
        String[] parts = src.split("\\.");
        return new String(decoder.decode(parts[part]));
    }

    public enum State {
        EMPTY, EXPIRED, ACTIVE_USER, ACTIVE_ADMIN
    }

    private class Payload {
        private String sub;
        private String roles;
        private Long iat;
        private Long exp;
    }

}
