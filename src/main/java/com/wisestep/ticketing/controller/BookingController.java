package com.wisestep.ticketing.controller;

import com.wisestep.ticketing.dto.BookingRequest;
import com.wisestep.ticketing.model.Seat;
import com.wisestep.ticketing.repository.SeatRepository;
import com.wisestep.ticketing.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Ticketing Controller", description = "Management APIs for seat initialization, retrieval, and booking")
public class BookingController {

    @Autowired private BookingService bookingService;
    @Autowired private SeatRepository seatRepository;

    @Operation(
        summary = "Initialize Event",
        description = "Resets the database and creates 100 fresh seats available for booking."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "System successfully initialized")
    })
    @PostMapping("/initialize")
    public String init() {
        seatRepository.deleteAll();
        List<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            seats.add(new Seat(i, "AVAILABLE", null));
        }
        seatRepository.saveAll(seats);
        return "System Initialized with 100 seats.";
    }

    @Operation(
        summary = "Get All Seats",
        description = "Returns a list of all 100 seats including their current status and user assignment."
    )
    @GetMapping("/seats")
    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }

    @Operation(
        summary = "Book Seats",
        description = "Processes a booking request for multiple seats. Price is calculated based on the global order of booking."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Booking successful, returns total price"),
        @ApiResponse(responseCode = "400", description = "Invalid request or seats already booked")
    })
    @PostMapping("/book")
    public ResponseEntity<?> book(@RequestBody BookingRequest request) {
        try {
            double price = bookingService.bookSeats(request.getSeatIds(), request.getUserName());
            return ResponseEntity.ok(Map.of("totalPrice", price));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}