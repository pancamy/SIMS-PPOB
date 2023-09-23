package com.sims.ppob.resolver;

import com.sims.ppob.constant.KeyConstant;
import com.sims.ppob.entity.Users;
import com.sims.ppob.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.server.ResponseStatusException;

@Component
@Slf4j
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private final UserRepository userRepository;

    public UserArgumentResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Users.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest servletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        String token = servletRequest.getHeader("Authorization");

        if(token == null || !token.startsWith("Bearer ")){
            return unauthorizedHandling();
        }

        try {
            String jwtToken = token.substring(7);
            if("null".equals(jwtToken)){
                return unauthorizedHandling();
            }

            String userId = extractUserIdFromToken(jwtToken);

            Users userParent = userRepository.getById(userId);
            if (userParent.getId() == null) {
                return unauthorizedHandling();
            }

            return userParent;
        } catch (ExpiredJwtException e){
            return unauthorizedHandling();
        }
    }

    private String extractUserIdFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(KeyConstant.secretKeyJWT).parseClaimsJws(token).getBody();

        return claims.get("user_id", String.class);
    }

    private ResponseStatusException unauthorizedHandling() {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token tidak tidak valid atau kadaluwarsa");
    }
}
