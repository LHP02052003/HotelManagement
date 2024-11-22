package com.example.hotel.service;

import com.example.hotel.model.Booking;
import com.example.hotel.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public long countRoomsByType(String roomType) {
        return bookingRepository.countByRoomType(roomType); // Cập nhật với kiểu String
    }
    public String getNextRoomNumber(String roomType) {
        int startFloor = 500; // Starting room number (500)
        int roomsPerFloor = 10; // 10 rooms per floor
        int maxFloor = 1909; // Maximum room number

        // Get the list of all bookings for this room type
        List<Booking> bookings = bookingRepository.findAll();

        // Create a set to store the base room numbers that have been booked (without suffix)
        Set<Integer> usedRooms = new HashSet<>();

        // Loop through all bookings and record the room numbers that have been used
        for (Booking booking : bookings) {
            // Extract the base room number (without any suffix like "A")
            int roomBaseNumber = Integer.parseInt(booking.getRoomNumber().substring(0, booking.getRoomNumber().length()));
            usedRooms.add(roomBaseNumber);
        }

        // Loop from 500 to 1909, and assign the next available room number
        for (int roomNumber = startFloor; roomNumber <= maxFloor; roomNumber++) {
            // Check if this room number is already booked or was previously used and then deleted
            if (!usedRooms.contains(roomNumber)) {
                // If not booked or deleted, return this room number as the next available one
                return String.valueOf(roomNumber);
            }

            // If the room number ends with "09", the next room should be on the next floor
            if (roomNumber % 100 == 9) {
                roomNumber = (roomNumber / 100) * 100 + 99; // Move to the next floor (e.g., 509 -> 600)
            }
        }

        // If no available rooms, throw an exception or handle it
        throw new IllegalStateException("No available rooms.");
    }



}
