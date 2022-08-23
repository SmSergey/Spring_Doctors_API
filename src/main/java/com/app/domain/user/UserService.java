package com.app.domain.user;

import com.app.domain.order.Order;
import com.app.domain.order.interfaces.OrderRepository;
import com.app.domain.order.interfaces.SortMethod;
import com.app.domain.user.dto.CreateUserRequest;
import com.app.domain.user.dto.GetOrdersRequest;
import com.app.domain.user.interfaces.UserRepository;
import com.app.security.services.SecurityService;
import com.app.utils.jwt.JwtTokenRepository;
import com.app.web.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenRepository jwtTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrderRepository orderRepository;

    public ResponseEntity<String> createUser(@RequestBody CreateUserRequest request) {
        User userToSave = new User();

        userToSave.setPassword(passwordEncoder.encode(request.getPassword()));
        userToSave.setLogin(request.getLogin());

        userRepository.save(userToSave);
        val token = jwtTokenRepository.generateTokens(userToSave.getId().toString());

        return new ApiResponse()
                .addField("accesstoken", token.getAccessToken())
                .addField("refreshtoken", token.getRefreshToken())
                .setStatus(200)
                .build();
    }

    public List<Order> getUserOrders(GetOrdersRequest request) {
        val dateWith = request.getDateWith();
        val dateFor = request.getDateFor();

        Sort sort = Sort.by(request.getSortMethod());
        sort = request.getSortType().equals(SortMethod.ASC)
                ? sort.ascending()
                : sort.descending();

        if (dateWith != null && dateFor != null) {
            return orderRepository.findByOwnerIdAndOrdersdateBetween(
                    Long.valueOf(SecurityService.getCurrentUserId()),
                    dateWith,
                    dateFor,
                    sort
            );
        } else return orderRepository.findByOwnerId(Long.valueOf(SecurityService.getCurrentUserId()), sort);
    }
}
