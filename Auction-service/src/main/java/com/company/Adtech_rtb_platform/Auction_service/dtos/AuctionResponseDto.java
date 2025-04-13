package com.company.Adtech_rtb_platform.Auction_service.dtos;


import com.company.Adtech_rtb_platform.Auction_service.enums.AuctionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuctionResponseDto {

    private Long id;
    private String itemName;
    private String itemDescription;
    private Double startingPrice;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private AuctionStatus status;
}
