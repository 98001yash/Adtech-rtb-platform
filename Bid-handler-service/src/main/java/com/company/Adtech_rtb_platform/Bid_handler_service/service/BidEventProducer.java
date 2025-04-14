package com.company.Adtech_rtb_platform.Bid_handler_service.service;


import com.company.Adtech_rtb_platform.Bid_handler_service.dtos.BidEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class BidEventProducer {

    private  final KafkaTemplate<String, BidEvent> kafkaTemplate;

    @Value("${kafka.topic.bid-events}")
    private String bidTopic;

    public void sendBidEvent(BidEvent event) {
        kafkaTemplate.send(bidTopic, event.getId().toString(), event);
        log.info("✅ Produced bid event: {}", event);
    }
}
