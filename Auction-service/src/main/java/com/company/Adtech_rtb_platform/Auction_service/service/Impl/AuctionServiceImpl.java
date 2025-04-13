package com.company.Adtech_rtb_platform.Auction_service.service.Impl;

import com.company.Adtech_rtb_platform.Auction_service.dtos.AuctionRequestDto;
import com.company.Adtech_rtb_platform.Auction_service.dtos.AuctionResponseDto;
import com.company.Adtech_rtb_platform.Auction_service.entities.Auction;
import com.company.Adtech_rtb_platform.Auction_service.enums.AuctionStatus;
import com.company.Adtech_rtb_platform.Auction_service.exceptions.ResourceNotFoundException;
import com.company.Adtech_rtb_platform.Auction_service.repository.AuctionRepository;
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


    @Override
    public AuctionResponseDto createAuction(AuctionRequestDto request) {
       Auction auction = modelMapper.map(request, Auction.class);
       auction.setStatus(AuctionStatus.CREATED);
       Auction saved = auctionRepository.save(auction);
       log.info("Auction created with ID: {}",saved.getId());
       return modelMapper.map(saved, AuctionResponseDto.class);
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
}
