package com.example.hotel.service;

import com.example.hotel.model.Dates;
import com.example.hotel.repository.DatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DatesService {
    private final DatesRepository datesRepository;
    @Autowired
    public DatesService(DatesRepository datesRepository) {
        this.datesRepository = datesRepository;
    }

    // Phương thức lưu chấm công
    public Dates save(Dates dates) {

        return datesRepository.save(dates);  // Sử dụng repository để lưu đối tượng Dates
    }

}
