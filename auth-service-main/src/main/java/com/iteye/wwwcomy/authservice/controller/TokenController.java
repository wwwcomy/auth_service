package com.iteye.wwwcomy.authservice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iteye.wwwcomy.authservice.service.TokenService;

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping(value = "/tokens")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    /**
     * Not a standard token instrospect endpoint.
     * 
     * @param token
     *            a input oauth token
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{token:.+}")
    public Map<String, Object> introspect(@PathVariable String token) {
        Map<String, Object> map = new HashMap<String, Object>();
        Claims claims = tokenService.getClaims(token);
        map.put("active", true);
        map.put("expires", claims.getExpiration().getTime());
        map.put("username", claims.getSubject());
        map.put("roles", claims.get("ROLE"));
        return map;
    }
}
