package com.company.Adtech_rtb_platform.Bid_handler_service.repository;

import com.company.Adtech_rtb_platform.Bid_handler_service.entities.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid,Long> {
}
