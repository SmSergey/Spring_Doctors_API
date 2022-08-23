package com.app.domain.order;

import com.app.domain.doctor.interfaces.DoctorRepository;
import com.app.domain.order.dto.CreateOrderRequest;
import com.app.domain.order.interfaces.OrderRepository;
import com.app.domain.user.interfaces.UserRepository;
import com.app.security.services.SecurityService;
import com.app.utils.messages.ErrorMessages;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;

    public Order createOrder(CreateOrderRequest req) {
        val doctor = doctorRepository.findById(req.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.DOCTOR_NOT_FOUND));

        val owner = userRepository.findById(Long.valueOf(SecurityService.getCurrentUserId()))
                .orElseThrow(() -> new UsernameNotFoundException(SecurityService.getCurrentUserId()));

        val order = new Order(req, owner, doctor);

        return orderRepository.save(order);
    }
}
