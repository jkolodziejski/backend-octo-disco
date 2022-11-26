package pl.put.backendoctodisco.utils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import pl.put.backendoctodisco.entity.User;
import pl.put.backendoctodisco.exceptions.TokenExpiredException;
import pl.put.backendoctodisco.exceptions.TokenNotFoundException;
import pl.put.backendoctodisco.exceptions.TokenUnauthorizedException;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;


public class AuthToken {

    private final static int MINUTE = 60 * 1000;
    private final static int EXPIRATION_TIME_SEC = 180 * MINUTE;

    private String token;

    public AuthToken(String token) {
        this.token = token;
    }

    public AuthToken(User user) {
        long now = System.currentTimeMillis();
        Date expirationDate = new Date((now + EXPIRATION_TIME_SEC));

        HashMap<String, Object> headers = new HashMap<>(1);
        headers.put("typ", "JWT");

        this.token = Jwts.builder()
                .setSubject(user.getLogin())
                .claim("roles", "user")
                .setIssuedAt(new Date(now))
                .setExpiration(expirationDate)
                .setHeader(headers)
                .signWith(getSecretKey())
                .compact();
    }

    public static State validateToken(User user) throws TokenNotFoundException, TokenExpiredException, TokenUnauthorizedException {
        State currentState = new AuthToken(user.getAuthToken()).validateSignature();
        switch (currentState) {
            case FAULTY -> throw new TokenNotFoundException();
            case EXPIRED -> throw new TokenExpiredException();
            case UNAUTHORIZED -> throw new TokenUnauthorizedException();
            default -> {
                return currentState;
            }
        }
    }

    static private SecretKey getSecretKey() {
        try {
            String unhashedKey = Files.readAllLines(Paths.get("authorization.key")).get(0);
            return Keys.hmacShaKeyFor(unhashedKey.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isEmpty() {
        return token.isEmpty();
    }

    public State validateSignature() {
        try {
            Jwts.parser()
                    .setSigningKey(getSecretKey())
                    .parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return State.EXPIRED;
        } catch (SignatureException e) {
            return State.UNAUTHORIZED;
        } catch (Exception e) {
            return State.FAULTY;
        }
        return State.ACTIVE_USER;
    }

    @Override
    public String toString() {
        return token;
    }

    private String encode(int part) {
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String src = this.token;
        String[] parts = src.split("\\.");
        return new String(decoder.decode(parts[part]));
    }

    public enum State {
        FAULTY, UNAUTHORIZED, EXPIRED, ACTIVE_USER, ACTIVE_ADMIN
    }

    private static class Payload {
        private String sub;
        private String roles;
        private Long iat;
        private Long exp;
    }

}
