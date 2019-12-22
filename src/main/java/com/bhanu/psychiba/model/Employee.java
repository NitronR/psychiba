package com.bhanu.psychiba.model;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public abstract class Employee extends Auditable {

    @NotBlank
    @Getter
    @Setter
    private String name;

    @NotBlank
    @Getter
    @Setter
    @Email
    private String email;

    @NotBlank
    @Getter
    @Setter
    private String address;

    @NotBlank
    @Getter
    @Setter
    private String phoneNumber;
}