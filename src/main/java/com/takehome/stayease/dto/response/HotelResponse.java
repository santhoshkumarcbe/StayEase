package com.takehome.stayease.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelResponse {

    private Long id;
    private String name;
    private String location;
    private String description;
    private int totalRooms;
    private int availableRooms;
}

