package com.company.Adtech_rtb_platform.Bid_handler_service.dtos;


import lombok.Data;

@Data
public class BidRequest {

    private Long id;
    private Double amount;
    private String bidderId;
}
