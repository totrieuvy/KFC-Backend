package com.example.Order_Project.service;

import com.example.Order_Project.entity.Account;
import com.example.Order_Project.repository.AuthenticationRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class TokenService {

    private final AuthenticationRepository authenticationRepository;

    private final String SECRET_KEY = "4bb6d1dfbafb64a681139d1586b6f1160d18159afd57c8c79136d7490630407vy";

    public TokenService(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    private SecretKey getSigninKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Account account){
        String token = Jwts.builder().subject(account.getId()+"")
                .issuedAt(new Date(System.currentTimeMillis())) //vd tạo lúc 10h30
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60*24)) //hết hạn sau 1 tiếng || nếu 1 ngày thì nhân thêm 24
                .signWith(getSigninKey())
                .compact();
        return token;
    }

    //verify token
    public Account getAccountByToken(String token){
        Claims claims = Jwts.parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String idString = claims.getSubject();
        long id = Long.parseLong(idString);

        return authenticationRepository.findAccountById(id);
    }
}
