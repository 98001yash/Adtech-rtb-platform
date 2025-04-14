package com.company.Adtech_rtb_platform.Bid_handler_service.service;

import com.company.Adtech_rtb_platform.Bid_handler_service.dtos.AuctionResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuctionEventConsumer {

    private final ObjectMapper objectMapper;

    public AuctionEventConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "auction-events", groupId = "bid-handler-group")
    public void listenAuctionEvents(String message) {
        try {
            AuctionResponseDto auction = objectMapper.readValue(message, AuctionResponseDto.class);
            log.info("✅ Received auction event: {}", auction);
            // You can now use the auction data to do further logic
        } catch (Exception e) {
            log.error("❌ Failed to parse auction event: {}", e.getMessage(), e);
        }
    }
}
