package com.takehome.stayease.controller;

import com.takehome.stayease.dto.request.BookingRequest;
import com.takehome.stayease.dto.response.BookingResponse;
import com.takehome.stayease.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/{hotelId}")
    public ResponseEntity<BookingResponse> createBooking(
            @PathVariable Long hotelId,
            @RequestBody BookingRequest request,
            Authentication authentication) {

            String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        return ResponseEntity.ok(
                bookingService.createBooking(hotelId, request, userEmail)
        );
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> getBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingService.getBooking(bookingId));
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.noContent().build();
    }
}