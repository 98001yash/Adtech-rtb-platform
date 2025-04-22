package com.company.Adtech_rtb_platform.Auction_service.service;

import com.company.Adtech_rtb_platform.Auction_service.dtos.BidResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BidEventConsumer {


    @KafkaListener(topics = "bid-events", groupId = "auction-service-group")
    public void consumeBidEvent(BidResponseDto bidResponseDto) {
        log.info(" Received bid event: {}", bidResponseDto);

    }
}
