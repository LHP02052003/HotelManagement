package com.example.hotel.service;

import com.example.hotel.model.Booking;
import com.example.hotel.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public void saveBooking(Booking booking) {
        bookingRepository.save(booking);
    }

    public List<Booking> findByUsername(String username) {
        return bookingRepository.findByUsername(username);
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    // Thêm phương thức này
    public List<Booking> findAllBookings() {
        return bookingRepository.findAll(); // Giả định rằng bookingRepository đã có phương thức này
    }

    public long countRoomsByType(int roomType) {
        return bookingRepository.countByRoomType(roomType);
    }
}
