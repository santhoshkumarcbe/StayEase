package com.takehome.stayease.service;

import lombok.extern.slf4j.Slf4j;
import java.time.LocalDate;

import com.takehome.stayease.dto.request.BookingRequest;
import com.takehome.stayease.dto.response.BookingResponse;
import com.takehome.stayease.entity.Booking;
import com.takehome.stayease.entity.Hotel;
import com.takehome.stayease.entity.User;
import com.takehome.stayease.exception.BadRequestException;
import com.takehome.stayease.exception.ResourceNotFoundException;
import com.takehome.stayease.repository.BookingRepository;
import com.takehome.stayease.repository.HotelRepository;
import com.takehome.stayease.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private UserRepository userRepository;

    public BookingResponse createBooking(Long hotelId, BookingRequest request, String userEmail) {

        if (request.getCheckInDate() == null || request.getCheckOutDate() == null) {
            throw new BadRequestException("Check-in and Check-out dates are required");
        }

        if (!request.getCheckInDate().isAfter(LocalDate.now())) {
            throw new BadRequestException("Check-in date must be in the future");
        }

        if (!request.getCheckOutDate().isAfter(request.getCheckInDate())) {
            throw new BadRequestException("Check-out must be after check-in");
        }

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found"));

        if (hotel.getAvailableRooms() <= 0) {
            throw new ResourceNotFoundException("No rooms available");
        }

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        hotel.setAvailableRooms(hotel.getAvailableRooms() - 1);
        hotelRepository.save(hotel);

        Booking booking = new Booking();
        booking.setHotel(hotel);
        booking.setUser(user);
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());

        Booking saved = bookingRepository.save(booking);
        log.info("Booking created with id {}", saved.getId());

        return new BookingResponse(
                saved.getId(),
                hotel.getId(),
                saved.getCheckInDate(),
                saved.getCheckOutDate()
        );
    }

    public BookingResponse getBooking(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        return new BookingResponse(
                booking.getId(),
                booking.getHotel().getId(),
                booking.getCheckInDate(),
                booking.getCheckOutDate()
        );
    }

    public void cancelBooking(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        Hotel hotel = booking.getHotel();

        hotel.setAvailableRooms(hotel.getAvailableRooms() + 1);
        hotelRepository.save(hotel);

        bookingRepository.delete(booking);
        log.info("Booking cancelled with id {}", bookingId);
    }
}
