package com.syscho.multi.web;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Employee {
    @Id
    private Long employeeId;

    private String name;
    private int age;

}
