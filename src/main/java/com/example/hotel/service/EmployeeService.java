package com.example.hotel.service;

import com.example.hotel.model.Dates;
import com.example.hotel.model.Employee;
import com.example.hotel.repository.DatesRepository;
import com.example.hotel.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository emloyeeRepository;
    private final DatesRepository datesRepository;

    public EmployeeService(EmployeeRepository emloyeeRepository, DatesRepository datesRepository) {
        this.emloyeeRepository = emloyeeRepository;
        this.datesRepository = datesRepository;
    }

    public  void deleteEmloyee(Long id) {
        emloyeeRepository.deleteById(id);
    }
    // Lấy thông tin nhân viên theo ID
    public Employee findEmployeeById(Long id) {
        return emloyeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Nhân viên không tồn tại"));
    }
    // Lưu hoặc cập nhật nhân viên
    public void saveEmployee(Employee employee) {
        emloyeeRepository.save(employee);
    }

    // Lấy danh sách nhân viên theo chức vụ
    public List<Employee> findByPosition(String position) {
        return emloyeeRepository.findByPosition(position);
    }

    // Lấy tất cả nhân viên
    public List<Employee> findAllEmployees() {

        return emloyeeRepository.findAll();
    }
    public List<Dates> findAllDataes() {

        return datesRepository.findAll();
    }

    // Ghi nhận giờ làm việc của nhân viên
    public void trackAttendance(Long employeeId, LocalDate date, int workingHours) {
        Employee employee = emloyeeRepository.findById(employeeId).orElseThrow();
        Dates dates = new Dates();
        dates.setEmployee(employee);
        dates.setDate(date);
        dates.setName(employee.getName());
        dates.setWorkingHours(workingHours);
        datesRepository.save(dates);
    }


//     Tính lương của nhân viên
    public double calculateSalary(Long employeeId) {
        Employee employee = emloyeeRepository.findById(employeeId).orElseThrow();
        List<Dates> attendances = employee.getAttendanceRecords();
        double totalWorkingHours = 0;

        for (Dates attendance : attendances) {
            totalWorkingHours += attendance.getWorkingHours();
        }

        // Giả sử lương mỗi giờ là 100000
        double salary = totalWorkingHours * 100000;
        employee.setSalary(salary);
        emloyeeRepository.save(employee);
        return salary;
    }

}
