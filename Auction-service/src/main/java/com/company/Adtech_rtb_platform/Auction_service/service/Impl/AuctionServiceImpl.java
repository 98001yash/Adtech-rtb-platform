package com.company.Adtech_rtb_platform.Auction_service.service.Impl;

import com.company.Adtech_rtb_platform.Auction_service.dtos.AuctionRequestDto;
import com.company.Adtech_rtb_platform.Auction_service.dtos.AuctionResponseDto;
import com.company.Adtech_rtb_platform.Auction_service.dtos.BidResponseDto;
import com.company.Adtech_rtb_platform.Auction_service.entities.Auction;
import com.company.Adtech_rtb_platform.Auction_service.enums.AuctionStatus;
import com.company.Adtech_rtb_platform.Auction_service.exceptions.ResourceNotFoundException;
import com.company.Adtech_rtb_platform.Auction_service.repository.AuctionRepository;
import com.company.Adtech_rtb_platform.Auction_service.service.AuctionEventProducer;
import com.company.Adtech_rtb_platform.Auction_service.service.AuctionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuctionServiceImpl implements AuctionService {

    private final AuctionRepository auctionRepository;
    private final ModelMapper modelMapper;
    private final AuctionEventProducer auctionEventProducer;


    @Override
    public AuctionResponseDto createAuction(AuctionRequestDto requestDto) {
        Auction auction = modelMapper.map(requestDto, Auction.class);
        auction.setStatus(AuctionStatus.CREATED);
        Auction savedAuction = auctionRepository.save(auction);

        AuctionResponseDto responseDto = modelMapper.map(savedAuction, AuctionResponseDto.class);
        auctionEventProducer.sendAuctionCreatedEvent(responseDto); // produce the event for the auction so that bid-handler can consume it
        return responseDto;
    }


    @Override
    public List<AuctionResponseDto> getAllAuctions() {
        List<Auction> auctions = auctionRepository.findAll();
        return auctions.stream()
                .map(auction->modelMapper.map(auction,AuctionResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AuctionResponseDto getAuctionById(Long id) {
        Auction auction = auctionRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Auction  not found with id: "+id));
        return modelMapper.map(auction, AuctionResponseDto.class);
    }

    @Override
    public AuctionResponseDto updateStatus(Long auctionId, AuctionStatus newStatus) {
       Auction auction = auctionRepository.findById(auctionId)
               .orElseThrow(() -> new ResourceNotFoundException("Auction id"+auctionId));

       log.info("Updating auction status: id={}, currentStatus={}, newStatus={}",auctionId,auction.getStatus(),newStatus);
       auction.setStatus(newStatus);
       auction = auctionRepository.save(auction);
       return modelMapper.map(auction, AuctionResponseDto.class);
    }

    @Override
    public void handleBidResponse(BidResponseDto bidResponseDto) {
        log.info("Handling bid response: {}", bidResponseDto);

        Optional<Auction> optionalAuction = auctionRepository.findById(bidResponseDto.getAuctionId());
        if (optionalAuction.isEmpty()) {
            log.warn("Auction not found for ID: {}", bidResponseDto.getAuctionId());
            return;
        }

        Auction auction = optionalAuction.get();

        if (auction.getStatus() == AuctionStatus.WINNER_DECLARED) {
            log.warn("Winner already declared for auction ID: {}", auction.getId());
            return;
        }

        // Check if this bid is the highest
        if (auction.getHighestBidAmount() == null ||
                bidResponseDto.getAmount().compareTo(auction.getHighestBidAmount()) > 0) {

            auction.setHighestBidAmount(bidResponseDto.getAmount());
            auction.setHighestBidderId(bidResponseDto.getUserId());
            log.info("New highest bid set: {} by User ID {}", bidResponseDto.getAmount(), bidResponseDto.getUserId());
        }

        // If auction has ended, declare winner
        if (auction.getEndTime().isBefore(LocalDateTime.now())) {
            auction.setStatus(AuctionStatus.WINNER_DECLARED);
            log.info("Auction ended. Winner declared: User ID {}", auction.getHighestBidderId());
        }

        auctionRepository.save(auction);

        AuctionResponseDto updatedAuction = modelMapper.map(auction, AuctionResponseDto.class);
        log.info("Updated auction after handling bid: {}", updatedAuction);
    }

}
