package com.example.hotel.service;

import com.example.hotel.model.EmployeeSalaryDTO;
import com.example.hotel.repository.SalaryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryService {


    private SalaryRepository salariesRepository;

    public SalaryService(SalaryRepository salariesRepository) {
        this.salariesRepository = salariesRepository;
    }
    public List<EmployeeSalaryDTO> getSalaryByMonthAndYear(int month, int year) {
        return salariesRepository.findByMonthAndYear(month, year);
    }
}
