package com.takehome.stayease.service;

import lombok.extern.slf4j.Slf4j;
import java.util.List;

import com.takehome.stayease.dto.request.HotelRequest;
import com.takehome.stayease.dto.response.HotelResponse;
import com.takehome.stayease.entity.Hotel;
import com.takehome.stayease.exception.BadRequestException;
import com.takehome.stayease.exception.ResourceNotFoundException;
import com.takehome.stayease.repository.HotelRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    public HotelResponse createHotel(HotelRequest request) {

        if (request.getName() == null || request.getName().isBlank()) {
            throw new BadRequestException("Hotel name is required");
        }
    
        if (request.getLocation() == null || request.getLocation().isBlank()) {
            throw new BadRequestException("Hotel location is required");
        }
    
        int totalRooms;
        if (request.getTotalRooms() == null || request.getTotalRooms() <= 0) {
            totalRooms = 1;
        } else {
            totalRooms = request.getTotalRooms();
        }
    
        int availableRooms;
        if (request.getAvailableRooms() == null || request.getAvailableRooms() < 0) {
            availableRooms = totalRooms;
        } else if (request.getAvailableRooms() > totalRooms) {
            availableRooms = totalRooms;
        } else {
            availableRooms = request.getAvailableRooms();
        }
    
        Hotel hotel = new Hotel();
        hotel.setName(request.getName());
        hotel.setLocation(request.getLocation());
        hotel.setDescription(request.getDescription());
        hotel.setTotalRooms(totalRooms);
        hotel.setAvailableRooms(availableRooms);
    
        Hotel saved = hotelRepository.save(hotel);
    
        return mapToResponse(saved);
    }
    
    public List<HotelResponse> getAllHotels() {
        return hotelRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public HotelResponse updateHotel(Long id, HotelRequest request) {

        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found"));
    
        if (request.getName() != null) {
            hotel.setName(request.getName());
        }
    
        if (request.getLocation() != null) {
            hotel.setLocation(request.getLocation());
        }
    
        if (request.getDescription() != null) {
            hotel.setDescription(request.getDescription());
        }
    
        if (request.getTotalRooms() != null && request.getTotalRooms() > 0) {
            hotel.setTotalRooms(request.getTotalRooms());
        }
    
        if (request.getAvailableRooms() != null && request.getAvailableRooms() >= 0) {
            hotel.setAvailableRooms(request.getAvailableRooms());
        }
    
        Hotel updated = hotelRepository.save(hotel);
    
        return mapToResponse(updated);
    }

    public void deleteHotel(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found"));

        hotelRepository.delete(hotel);
        log.info("Hotel deleted with id {}", id);
    }

    private HotelResponse mapToResponse(Hotel hotel) {
        return new HotelResponse(
                hotel.getId(),
                hotel.getName(),
                hotel.getLocation(),
                hotel.getDescription(),
                hotel.getTotalRooms(),
                hotel.getAvailableRooms()
        );
    }
}
