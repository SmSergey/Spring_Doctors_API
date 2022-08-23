package com.app.domain.authorization;


import com.app.domain.authorization.dto.LoginRequest;
import com.app.domain.user.interfaces.UserRepository;
import com.app.utils.jwt.JwtTokenRepository;
import com.app.web.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenRepository jwtTokenRepository;
    private final UserRepository userRepository;

    public ResponseEntity<String> performLogin(LoginRequest req) {
        val authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getLogin(), req.getPassword()
                )
        );
        val securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        val userId = userRepository.findByLogin(req.getLogin()).get().getId();
        val tokens = jwtTokenRepository.generateTokens(userId.toString());

        return new ApiResponse()
                .setStatus(HttpStatus.OK.value())
                .addField("accesstoken", tokens.getAccessToken())
                .addField("refreshtoken", tokens.getRefreshToken()).build();

    }
}
