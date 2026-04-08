package com.takehome.stayease.controllerTest;

import com.takehome.stayease.controller.BookingController;
import com.takehome.stayease.dto.response.BookingResponse;
import com.takehome.stayease.security.JwtFilter;
import com.takehome.stayease.security.CustomUserDetailsService;
import com.takehome.stayease.service.BookingService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
@AutoConfigureMockMvc(addFilters = false)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @MockBean
    private JwtFilter jwtFilter;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void shouldGetBooking() throws Exception {

        BookingResponse response =
                new BookingResponse(1L, 1L, LocalDate.now(), LocalDate.now().plusDays(2));

        Mockito.when(bookingService.getBooking(1L)).thenReturn(response);

        mockMvc.perform(get("/api/bookings/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingId").value(1));
    }
}
