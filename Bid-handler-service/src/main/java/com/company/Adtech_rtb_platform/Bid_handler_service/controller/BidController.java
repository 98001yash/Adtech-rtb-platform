package com.company.Adtech_rtb_platform.Bid_handler_service.controller;


import com.company.Adtech_rtb_platform.Bid_handler_service.dtos.BidRequest;
import com.company.Adtech_rtb_platform.Bid_handler_service.entities.Bid;
import com.company.Adtech_rtb_platform.Bid_handler_service.exceptions.ResourceNotFoundException;
import com.company.Adtech_rtb_platform.Bid_handler_service.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bids")
@RequiredArgsConstructor
public class BidController {

    private final BidService bidService;

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
}
