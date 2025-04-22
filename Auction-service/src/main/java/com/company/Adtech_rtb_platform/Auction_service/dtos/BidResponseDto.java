package com.company.Adtech_rtb_platform.Auction_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BidResponseDto {

    private Long id;
    private Long auctionId;
    private Long userId;
    private BigDecimal amount;
    private String timeStamp;

}
