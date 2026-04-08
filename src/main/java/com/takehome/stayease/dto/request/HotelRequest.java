package com.takehome.stayease.dto.request;

import lombok.Data;
@Data
public class HotelRequest {

    private String name;
    private String location;
    private String description;
    private Integer totalRooms;
    private Integer availableRooms;
}
