package com.company.Adtech_rtb_platform.Auction_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuctionRequestDto {

    private String itemName;
    private String itemDescription;
    private Double startingTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
