package com.company.Adtech_rtb_platform.Bid_handler_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BidRequest {

    private Long id;
    private Double amount;
    private String bidderId;
}
