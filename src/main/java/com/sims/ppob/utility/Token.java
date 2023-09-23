package com.sims.ppob.utility;

import com.sims.ppob.constant.KeyConstant;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
@Slf4j
public class Token {

    public String getToken(String email, Date accessTokenExpirationDate) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(email)
                .claim("email", email)
                .setExpiration(accessTokenExpirationDate)
                .signWith(SignatureAlgorithm.HS256, KeyConstant.secretKeyJWT)
                .compact();
    }
}
