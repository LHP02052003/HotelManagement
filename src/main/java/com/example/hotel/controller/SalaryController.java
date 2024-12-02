package com.example.hotel.controller;

import com.example.hotel.model.EmployeeSalaryDTO;
import com.example.hotel.service.SalaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class SalaryController {


    private SalaryService salaryService;

    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @GetMapping("/admin/salarys")
    public String getSalaries(Model model) {

        // Lấy tháng và năm hiện tại
        LocalDate currentDate = LocalDate.now();
        int currentMonth = currentDate.getMonthValue(); // Lấy tháng hiện tại
        int currentYear = currentDate.getYear(); // Lấy năm hiện tại

        // Lấy danh sách lương của nhân viên theo tháng và năm hiện tại
        List<EmployeeSalaryDTO> salaryReports = salaryService.getSalaryByMonthAndYear(currentMonth, currentYear);

        // Thêm tháng và năm hiện tại vào model
        model.addAttribute("currentMonth", currentMonth);
        model.addAttribute("currentYear", currentYear);

        // Kiểm tra nếu không có dữ liệu lương
        if (salaryReports == null || salaryReports.isEmpty()) {
            model.addAttribute("message", "Hiện tại không có dữ liệu lương cho tháng " + currentMonth + " và năm " + currentYear + ".");
            model.addAttribute("salaryReports", List.of()); // Đảm bảo danh sách không null
        } else {
            model.addAttribute("salaryReports", salaryReports);
        }

        return "salaryResult";
    }
    @GetMapping("/admin/salarys/filter")
    public String filterSalaries(
            @RequestParam("month") int month,
            @RequestParam("year") int year,
            Model model) {

        // Lấy danh sách lương theo tháng và năm
        List<EmployeeSalaryDTO> salaryReports = salaryService.getSalaryByMonthAndYear(month, year);

        // Thêm tháng và năm hiện tại vào model
        model.addAttribute("currentMonth", month);
        model.addAttribute("currentYear", year);

        if (salaryReports == null || salaryReports.isEmpty()) {
            model.addAttribute("message", "Không có dữ liệu lương cho tháng " + month + " và năm " + year + ".");
            model.addAttribute("salaryReports", List.of());
        } else {
            model.addAttribute("salaryReports", salaryReports);
        }

        return "salaryResult";
    }
}

