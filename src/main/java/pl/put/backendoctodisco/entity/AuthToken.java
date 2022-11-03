package pl.put.backendoctodisco.entity;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

public class AuthToken {

    private final static int expirationTime = 10000;

    private String token;
    private Date expirationDate;

    public AuthToken(User user) {
        long now = System.currentTimeMillis();
        String key;
        try {
            key = Files.readAllLines(Paths.get("authorization.key")).get(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.expirationDate = new Date(now + expirationTime);

        this.token = Jwts.builder()
                .setSubject(user.getLogin())
                .claim("roles", "user")
                .setIssuedAt(new Date(now))
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, key).compact();
    }

    @Override
    public String toString() {
        return token;
    }
}
