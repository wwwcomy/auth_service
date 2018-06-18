package com.iteye.wwwcomy.authservice.service.rp;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

/**
 * This class is for token information extraction and validation, so that the
 * client won't need to reference to other classes (like for token generating).
 *
 */
@Service
public class TokenValidationService {

    // TODO make it configurable
    public static final String signingKey = "123456";

    public Claims getClaims(String jwtString) {
        return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(jwtString).getBody();
    }

    public String getSubject(String jwtString) {
        return getClaims(jwtString).getSubject();
    }

    public boolean validate(String jwtString) {
        try {
            getSubject(jwtString);
            return true;
        } catch (SignatureException e) {
            return false;
        }
    }

    public boolean hasExpired(String jwtString) {
        Claims claims;
        try {
            claims = getClaims(jwtString);
        } catch (SignatureException e) {
            return true;
        }
        DateTime dateTime = new DateTime(claims.getExpiration());
        if (dateTime.isAfterNow()) {
            return false;
        }
        return true;
    }
}
