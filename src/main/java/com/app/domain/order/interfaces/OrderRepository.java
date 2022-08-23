package com.app.domain.order.interfaces;

import com.app.domain.order.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;


public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByOwnerId(Long id, Sort sort);
    List<Order> findByOwnerIdAndOrdersdateBetween(Long id, Date dateWith, Date dateFor, Sort sort);
}
