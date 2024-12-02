package com.example.hotel.repository;

import com.example.hotel.model.EmployeeSalaryDTO;
import com.example.hotel.model.Salarys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public  interface SalaryRepository extends JpaRepository<Salarys, Long> {
    @Query("SELECT new com.example.hotel.model.EmployeeSalaryDTO( " +
            "e.id, e.name, e.salary, YEAR(d.date), MONTH(d.date), " +
            "SUM(d.workingHours), (e.salary * SUM(d.workingHours)) ) " +
            "FROM Employee e JOIN e.attendanceRecords d " +
            "WHERE YEAR(d.date) = :year AND MONTH(d.date) = :month " +
            "GROUP BY e.id, e.name, e.salary, YEAR(d.date), MONTH(d.date) " +
            "ORDER BY e.name")
    List<EmployeeSalaryDTO> findByMonthAndYear(@Param("month") int month, @Param("year") int year);
}
