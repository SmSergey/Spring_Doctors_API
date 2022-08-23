package com.app.domain.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateOrderRequest {

    @NotBlank
    private String patient;

    @JsonProperty("ordersdate")
    private String ordersDate;

    @NotBlank
    private String complaints;

    @NotBlank
    private String doctorId;

    public Long getDoctorId() {
        return Long.valueOf(doctorId);
    }
}
