package com.wisestep.ticketing.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    @Id
    // DO NOT add @GeneratedValue here. 
    // We want to manually set IDs 1, 2, 3... 100.
    private Integer id; 
    
    private String status;
    private String userName;
}