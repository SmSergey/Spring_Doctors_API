package com.app.domain.doctor;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
public class Doctor {

    @Id
    @GeneratedValue
    private Long id;

    private String fullname;
}
