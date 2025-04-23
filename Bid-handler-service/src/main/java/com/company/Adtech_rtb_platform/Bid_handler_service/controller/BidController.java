package com.company.Adtech_rtb_platform.Bid_handler_service.controller;


import com.company.Adtech_rtb_platform.Bid_handler_service.dtos.BidEvent;
import com.company.Adtech_rtb_platform.Bid_handler_service.dtos.BidRequest;
import com.company.Adtech_rtb_platform.Bid_handler_service.entities.Bid;
import com.company.Adtech_rtb_platform.Bid_handler_service.exceptions.ResourceNotFoundException;
import com.company.Adtech_rtb_platform.Bid_handler_service.service.BidEventProducer;
import com.company.Adtech_rtb_platform.Bid_handler_service.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/bids")
@RequiredArgsConstructor
public class BidController {

    private final BidService bidService;
    private final BidEventProducer bidEventProducer;

    @PostMapping
    public ResponseEntity<Bid> createBid(@RequestBody BidRequest bidRequest){
        Bid bid = bidService.createBid(bidRequest);
        return new ResponseEntity<>(bid, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Bid> getAllBids(){
        return bidService.getAllBids();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bid> getBidById(@PathVariable Long id){
        try{
            Bid bid = bidService.getRideById(id);
            return new ResponseEntity<>(bid, HttpStatus.OK);
        }catch(ResourceNotFoundException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<String> submitBid(@RequestBody BidRequest bidRequest) {
        try {
            bidService.submitBid(bidRequest);
            return ResponseEntity.ok("Bid submitted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error submitting bid: " + e.getMessage());
        }
    }
}
