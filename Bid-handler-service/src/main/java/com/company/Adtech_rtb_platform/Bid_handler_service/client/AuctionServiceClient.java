package com.company.Adtech_rtb_platform.Bid_handler_service.client;

import com.company.Adtech_rtb_platform.Bid_handler_service.advices.ApiResponse;
import com.company.Adtech_rtb_platform.Bid_handler_service.dtos.BidResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "Auction-service", url="http://localhost:9020")
public interface AuctionServiceClient {


    @PostMapping("/auction/bid-response")
    ApiResponse<String> handleBidResponse(@RequestBody BidResponseDto bidResponseDto);
}
