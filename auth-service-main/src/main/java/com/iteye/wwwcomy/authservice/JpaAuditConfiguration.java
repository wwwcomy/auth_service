package com.iteye.wwwcomy.authservice;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.iteye.wwwcomy.authservice.entity.User;

@Configuration
public class JpaAuditConfiguration implements AuditorAware<String> {
    private static final String DEFAULT_AUDITOR = "N/A";

    @Override
    public String getCurrentAuditor() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        if (ctx == null || ctx.getAuthentication() == null || ctx.getAuthentication().getPrincipal() == null) {
            return DEFAULT_AUDITOR;
        }
        Object principal = ctx.getAuthentication().getPrincipal();
        if (principal.getClass().isAssignableFrom(User.class)) {
            return ((User) principal).getName();
        } else {
            return DEFAULT_AUDITOR;
        }
    }
}