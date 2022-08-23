package com.app.domain.user;

import com.app.domain.doctor.interfaces.DoctorRepository;
import com.app.domain.user.dto.CreateUserRequest;
import com.app.domain.user.dto.GetOrdersRequest;
import com.app.web.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final DoctorRepository doctorRepository;

    @PostMapping(value = "/createNewUser")
    public ResponseEntity<String> createUser(@RequestBody @Validated CreateUserRequest request) {
        return userService.createUser(request);
    }

    @GetMapping("/getAllUserOrders")
    public ResponseEntity<String> getOrders(@Validated GetOrdersRequest request) {

        val orders = userService.getUserOrders(request);
        val doctors = doctorRepository.findAll();

        return new ApiResponse()
                .setStatus(HttpStatus.OK.value())
                .addField("orders", orders)
                .addField("doctors", doctors).build();
    }
}
