package com.app.security.services;

import com.app.domain.user.interfaces.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        val user = userRepository.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new User(String.valueOf(user.getLogin()), user.getPassword(),  new ArrayList<>());
    }
}
