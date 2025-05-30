package com.boram.look.global.util;

import com.boram.look.global.security.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ResponseUtil {

    public static ResponseEntity<?> buildUnauthorizedResponseEntity(String message) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }

    public static void responseAccessToken(
            ObjectMapper objectMapper,
            HttpServletResponse response,
            String accessToken
    ) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(Map.of("access", accessToken)));
    }

    public static void responseRefreshToken(
            HttpServletResponse response,
            JwtProvider jwtProvider,
            String refreshToken
    ) {
        jwtProvider.buildRefreshTokenCookie(refreshToken, response);
    }

    public static String buildRoleString(Collection<? extends GrantedAuthority> authorities) {
        String roleString = "";
        if (authorities != null) {
            roleString = authorities.stream()
                    .filter(Objects::nonNull)
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));
        }
        return roleString;
    }

    public static String getStateIdFromCallbackUrl(String urlDecoded) {
        URI uri = URI.create(urlDecoded);
        String query = uri.getQuery();
        Map<String, String> paramMap = Arrays.stream(query.split("&"))
                .map(s -> s.split("="))
                .collect(Collectors.toMap(a -> a[0], a -> a.length > 1 ? a[1] : ""));
        return paramMap.get("stateId");
    }

}
