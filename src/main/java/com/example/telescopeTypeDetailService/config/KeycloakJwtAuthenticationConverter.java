package com.example.telescopeTypeDetailService.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class KeycloakJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    // Укажи ID твоего клиента в Keycloak
    private static final String CLIENT_ID = "lk_star";

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = extractAuthorities(jwt);
        return new JwtAuthenticationToken(jwt, authorities);
    }

    @SuppressWarnings("unchecked")
    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        Object resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess == null || !(resourceAccess instanceof java.util.Map)) {
            return Collections.emptyList();
        }

        java.util.Map<String, Object> resourceAccessMap = (java.util.Map<String, Object>) resourceAccess;
        Object clientRoles = resourceAccessMap.get(CLIENT_ID);

        if (clientRoles == null || !(clientRoles instanceof java.util.Map)) {
            return Collections.emptyList();
        }

        java.util.Map<String, Object> clientMap = (java.util.Map<String, Object>) clientRoles;
        Object roles = clientMap.get("roles");

        if (roles == null || !(roles instanceof List)) {
            return Collections.emptyList();
        }

        List<String> roleNames = (List<String>) roles;
        return roleNames.stream()
                .map(role -> (GrantedAuthority) () -> "ROLE_" + role)
                .collect(Collectors.toList());
    }
}