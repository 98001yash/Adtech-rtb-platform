package com.company.Adtech_rtb_platform.Bid_handler_service.service;


import com.company.Adtech_rtb_platform.Bid_handler_service.advices.ApiResponse;
import com.company.Adtech_rtb_platform.Bid_handler_service.client.AuctionServiceClient;
import com.company.Adtech_rtb_platform.Bid_handler_service.dtos.BidRequest;
import com.company.Adtech_rtb_platform.Bid_handler_service.dtos.BidResponseDto;
import com.company.Adtech_rtb_platform.Bid_handler_service.entities.Bid;
import com.company.Adtech_rtb_platform.Bid_handler_service.exceptions.ResourceNotFoundException;
import com.company.Adtech_rtb_platform.Bid_handler_service.repository.BidRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BidService {

    private final BidRepository bidRepository;
    private final ModelMapper modelMapper;
    private final AuctionServiceClient auctionServiceClient;

    // create a  bid
    public Bid createBid(BidRequest bidRequest){
        Bid bid = modelMapper.map(bidRequest, Bid.class);
        return bidRepository.save(bid);
    }

    // get All bids
    public List<Bid> getAllBids(){
        return bidRepository.findAll();
    }


    // get a specific bid by ID
    public Bid getRideById(Long id) throws ResourceNotFoundException{
        Optional<Bid> bid =bidRepository.findById(id);
        if(bid.isPresent()){
            return bid.get();
        }else {
            throw new ResourceNotFoundException("Bid not found with id: "+id);
        }
    }

    public void submitBid(BidRequest bidRequest) {
        log.info("Submitting bid: {}", bidRequest);

        BidResponseDto bidResponseDto = BidResponseDto.builder()
                .id(1L) // Ideally, use a generated ID or from DB
                .auctionId(123L) // Replace with actual auction ID from bidRequest if available
                .userId(Long.valueOf(bidRequest.getBidderId()))
                .amount(BigDecimal.valueOf(bidRequest.getAmount()))
                .timeStamp(java.time.LocalDateTime.now().toString())
                .build();

        try {
            ApiResponse<String> response = auctionServiceClient.handleBidResponse(bidResponseDto);
            log.info("Response from AuctionService: {}", response.getData());
        } catch (Exception e) {
            log.error("Error communicating with AuctionService: {}", e.getMessage(), e);
        }
    }

}
