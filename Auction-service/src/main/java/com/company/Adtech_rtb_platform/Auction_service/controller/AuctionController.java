package com.company.Adtech_rtb_platform.Auction_service.controller;


import com.company.Adtech_rtb_platform.Auction_service.advices.ApiResponse;
import com.company.Adtech_rtb_platform.Auction_service.dtos.AuctionRequestDto;
import com.company.Adtech_rtb_platform.Auction_service.dtos.AuctionResponseDto;
import com.company.Adtech_rtb_platform.Auction_service.enums.AuctionStatus;
import com.company.Adtech_rtb_platform.Auction_service.service.AuctionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auction")
@RequiredArgsConstructor
@Slf4j
public class AuctionController {

    private final AuctionService auctionService;

    @PostMapping
    public ResponseEntity<AuctionResponseDto> createAuction(@RequestBody AuctionRequestDto requestDto){
        log.info("Creating new auction....");
        AuctionResponseDto response = auctionService.createAuction(requestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AuctionResponseDto>> getAllAuctions(){
        log.info("Fetching all auctions....");
        return ResponseEntity.ok(auctionService.getAllAuctions());
    }
    @GetMapping("/{id}")
    public ResponseEntity<AuctionResponseDto> getAuctionById(@PathVariable Long id){
        log.info("Fetching auction with id: {}",id);
        return ResponseEntity.ok(auctionService.getAuctionById(id));
    }

    @PutMapping("/{auctionId}/status")
    public ResponseEntity<ApiResponse<AuctionResponseDto>> updateAuctionStatus(
            @PathVariable Long auctionId,
            @RequestParam AuctionStatus status) {

        log.info("Received request to update status of auction {} to {}", auctionId, status);
        AuctionResponseDto updatedAuction = auctionService.updateStatus(auctionId, status);

        return ResponseEntity.ok(
                ApiResponse.<AuctionResponseDto>builder()
                        .timeStamp(java.time.LocalDateTime.now())
                        .data(updatedAuction)
                        .error(null)
                        .build()
        );
    }


}
