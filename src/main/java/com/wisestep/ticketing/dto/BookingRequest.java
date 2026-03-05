package com.wisestep.ticketing.dto;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class BookingRequest {
    private List<Integer> seatIds;
    private String userName;
}