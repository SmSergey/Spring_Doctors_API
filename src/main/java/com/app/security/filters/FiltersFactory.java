package com.app.security.filters;

import com.app.domain.user.interfaces.UserRepository;
import com.app.security.filters.fwt.JwtFilter;
import com.app.utils.jwt.JwtTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FiltersFactory {

    private final JwtTokenRepository jwtTokenRepository;
    private final UserRepository userRepository;

    public JwtFilter getJwtFilter() {
        return new JwtFilter(jwtTokenRepository, userRepository);
    }
}
