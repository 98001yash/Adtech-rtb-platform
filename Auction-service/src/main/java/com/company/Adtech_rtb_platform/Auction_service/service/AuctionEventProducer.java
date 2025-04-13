package com.company.Adtech_rtb_platform.Auction_service.service;

import com.company.Adtech_rtb_platform.Auction_service.dtos.AuctionResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuctionEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper; // Automatically configured by Spring Boot

    public void sendAuctionCreatedEvent(AuctionResponseDto auction) {
        String topic = "auction-events";

        try {
            String message = objectMapper.writeValueAsString(auction);
            kafkaTemplate.send(topic, message);
            log.info("✅ Produced auction created event to Kafka: {}", message);
        } catch (JsonProcessingException e) {
            log.error("❌ Failed to convert auction to JSON: {}", e.getMessage(), e);
        }
    }
}
