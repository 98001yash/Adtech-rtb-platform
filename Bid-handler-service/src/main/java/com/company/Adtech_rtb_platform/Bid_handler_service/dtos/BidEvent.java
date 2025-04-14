package com.company.Adtech_rtb_platform.Bid_handler_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BidEvent {

    private Long id;
    private Double amount;
    private String bidderId;
    private LocalDateTime bidTime;
}
