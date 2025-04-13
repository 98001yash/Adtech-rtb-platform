package com.company.Adtech_rtb_platform.Auction_service.service;

import com.company.Adtech_rtb_platform.Auction_service.dtos.AuctionRequestDto;
import com.company.Adtech_rtb_platform.Auction_service.dtos.AuctionResponseDto;
import com.company.Adtech_rtb_platform.Auction_service.enums.AuctionStatus;

import java.util.List;

public interface AuctionService {

    AuctionResponseDto createAuction(AuctionRequestDto request);
    List<AuctionResponseDto> getAllAuctions();
    AuctionResponseDto getAuctionById(Long id);
    AuctionResponseDto updateStatus(Long auctionId, AuctionStatus newStatus);
}
