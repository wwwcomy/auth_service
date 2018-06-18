package com.iteye.wwwcomy.authservice.service;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.iteye.wwwcomy.authservice.entity.Token;
import com.iteye.wwwcomy.authservice.entity.User;
import com.iteye.wwwcomy.authservice.repository.TokenRepository;
import com.iteye.wwwcomy.authservice.service.rp.TokenValidationService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

    @Value("${token.expiration.day:7}")
    private int tokenExpirationDay;

    private static final String ISSUER = "AUTH_SERVICE";
    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private TokenValidationService tokenValidationService;

    @Autowired
    private UserService userSerivce;

    public String createAndStoreToken(String userName) {
        String tokenStr = generateToken(userName);
        tokenRepository.save(new Token(tokenStr));
        return tokenStr;
    }

    /**
     * Generate a new token with subject as the input user name, the expiration date
     * will be 365 days from the creation date of the token
     * 
     * @param userName
     * @return
     */
    public String generateToken(String userName) {
        DateTime now = DateTime.now(DateTimeZone.UTC);
        User user = userSerivce.findUser(userName);
        DateTime expiration = now.plus(Days.days(tokenExpirationDay).toStandardDuration());
        return Jwts.builder().setSubject(userName).setIssuedAt(now.toDate()).setExpiration(expiration.toDate())
                .setIssuer(ISSUER).claim("ROLE", user.getRoleNames())
                .signWith(SignatureAlgorithm.HS512, TokenValidationService.signingKey).compact();
    }

    public Claims getClaims(String jwtString) {
        return tokenValidationService.getClaims(jwtString);
    }

    public String getSubject(String jwtString) {
        return tokenValidationService.getSubject(jwtString);
    }

    public boolean validate(String jwtString) {
        return tokenValidationService.validate(jwtString);
    }
}
