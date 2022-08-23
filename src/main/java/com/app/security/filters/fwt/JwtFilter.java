package com.app.security.filters.fwt;

import com.app.domain.user.interfaces.UserRepository;
import com.app.security.interfaces.Roles;
import com.app.utils.jwt.JwtTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenRepository jwtTokenRepository;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        val tokenString = jwtTokenRepository.fetchTokenFromRequest(request);
        if (tokenString != null && jwtTokenRepository.isTokenValid(tokenString)) {
            try {
                val tokenData = jwtTokenRepository.parseToken(tokenString);
                val userId = new JSONObject(tokenData.get("user").toString()).get("userId").toString();
                val user = userRepository.findById(Long.valueOf(userId));

                if (user.isPresent()) {
                    GrantedAuthority authority = new SimpleGrantedAuthority(Roles.COMMON_USER);
                    val authentication = new UsernamePasswordAuthenticationToken(user.get().getId(), tokenString, List.of(authority));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (JSONException err) {
                logger.error(err.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}
