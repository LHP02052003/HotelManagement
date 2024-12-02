package com.example.hotel.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeSalaryDTO {
    private Long employeeId;
    private String employeeName;
    private double hourlySalary;
    private int year;
    private int month;
    private long totalWorkingHours;
    private double totalSalary;


}
