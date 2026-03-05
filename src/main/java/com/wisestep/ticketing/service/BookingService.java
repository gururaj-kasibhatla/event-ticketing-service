package com.wisestep.ticketing.service;

import com.wisestep.ticketing.model.Seat;
import com.wisestep.ticketing.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private SeatRepository seatRepository;

    @Transactional
    public double bookSeats(List<Integer> seatIds, String userName) {
        // 1. Get count of already booked seats to know the starting "Order"
        int alreadyBooked = (int) seatRepository.countByStatus("BOOKED");
        
        // 2. Fetch requested seats with a DB lock
        List<Seat> requestedSeats = seatRepository.findAllByIdWithLock(seatIds);

        // 3. Validation: Are they already taken?
        for (Seat s : requestedSeats) {
            if ("BOOKED".equals(s.getStatus())) {
                throw new RuntimeException("Seat " + s.getId() + " is already taken!");
            }
        }

        double totalCost = 0;
        int currentOrder = alreadyBooked;

        // 4. Process each seat and calculate price based on order
        for (Seat s : requestedSeats) {
            currentOrder++; // Increment order for every single seat in this transaction
            totalCost += calculatePriceByOrder(currentOrder);
            
            s.setStatus("BOOKED");
            s.setUserName(userName);
        }

        seatRepository.saveAll(requestedSeats);
        return totalCost;
    }

    private double calculatePriceByOrder(int order) {
        if (order <= 50) return 50.0;
        if (order <= 80) return 75.0;
        return 100.0;
    }
}