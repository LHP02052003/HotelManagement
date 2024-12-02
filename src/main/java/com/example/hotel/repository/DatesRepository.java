package com.example.hotel.repository;

import com.example.hotel.model.Dates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DatesRepository extends JpaRepository<Dates, Long> {
    // Phương thức truy vấn tất cả các bản ghi Dates theo employee_id
    List<Dates> findByEmployeeId(Long employeeId);

    // Phương thức truy vấn tất cả các bản ghi Dates theo ngày (date)
    List<Dates> findByDate(LocalDate date);

    // Truy vấn số giờ làm việc của một nhân viên trong một tháng
    @Query("SELECT COALESCE(SUM(d.workingHours), 0) FROM Dates d " +
            "WHERE d.employee.id = :employeeId " +
            "AND MONTH(d.date) = :month " +
            "AND YEAR(d.date) = :year")
    int sumWorkingHoursByEmployeeAndMonthAndYear(
            @Param("employeeId") Long employeeId,
            @Param("month") int month,
            @Param("year") int year);
}
