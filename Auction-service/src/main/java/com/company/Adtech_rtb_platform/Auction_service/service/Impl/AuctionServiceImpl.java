package com.company.Adtech_rtb_platform.Auction_service.service.Impl;

import com.company.Adtech_rtb_platform.Auction_service.dtos.AuctionRequestDto;
import com.company.Adtech_rtb_platform.Auction_service.dtos.AuctionResponseDto;
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

import java.util.List;
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

        auctionEventProducer.sendAuctionCreatedEvent(responseDto); // 👈 new line

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
}
