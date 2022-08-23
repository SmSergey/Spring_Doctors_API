package com.app.domain.order;

import com.app.domain.doctor.Doctor;
import com.app.domain.order.dto.CreateOrderRequest;
import com.app.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    private String patient;

    private String complaints;

    private Date ordersdate;

    @ManyToOne
    @JoinColumn(name = "doctor")
    private Doctor doctor;

    @ManyToOne
    private User owner;

    @CreatedDate
    private Date createdAt;


    public Order(CreateOrderRequest request, User owner, Doctor doctor) {

        this.patient = request.getPatient();
        this.complaints = request.getComplaints();
        this.owner = owner;
        this.doctor = doctor;

        try {
            this.ordersdate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getOrdersDate());
        } catch (ParseException err) {
            err.printStackTrace();
        }
    }
}
