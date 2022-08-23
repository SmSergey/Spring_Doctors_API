package com.app.domain.authorization;


import com.app.domain.authorization.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/authorizationUser")
    public ResponseEntity<String> login(
            @RequestBody @Validated LoginRequest req
    ) {
        return authService.performLogin(req);
    }

}
