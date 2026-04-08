package com.takehome.stayease.controllerTest;

import com.takehome.stayease.controller.HotelController;
import com.takehome.stayease.dto.response.HotelResponse;
import com.takehome.stayease.security.JwtFilter;
import com.takehome.stayease.security.CustomUserDetailsService;
import com.takehome.stayease.service.HotelService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HotelController.class)
@AutoConfigureMockMvc(addFilters = false)
class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HotelService hotelService;

    @MockBean
    private JwtFilter jwtFilter;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void shouldGetAllHotels() throws Exception {

        List<HotelResponse> hotels = List.of(
                new HotelResponse(1L, "Hotel1", "Location", "Desc", 10, 5)
        );

        Mockito.when(hotelService.getAllHotels()).thenReturn(hotels);

        mockMvc.perform(get("/api/hotels"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Hotel1"));
    }
}
