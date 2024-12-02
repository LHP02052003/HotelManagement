package com.example.hotel.repository;

import com.example.hotel.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByName(String name);

    // Tìm nhân viên theo chức vụ
    List<Employee> findByPosition(String position);
}
