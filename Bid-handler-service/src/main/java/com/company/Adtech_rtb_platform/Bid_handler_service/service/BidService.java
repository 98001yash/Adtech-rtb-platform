package com.company.Adtech_rtb_platform.Bid_handler_service.service;


import com.company.Adtech_rtb_platform.Bid_handler_service.dtos.BidRequest;
import com.company.Adtech_rtb_platform.Bid_handler_service.entities.Bid;
import com.company.Adtech_rtb_platform.Bid_handler_service.exceptions.ResourceNotFoundException;
import com.company.Adtech_rtb_platform.Bid_handler_service.repository.BidRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BidService {

    private final BidRepository bidRepository;
    private final ModelMapper modelMapper;

    // create a  bid
    public Bid createBud(BidRequest bidRequest){
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
}
