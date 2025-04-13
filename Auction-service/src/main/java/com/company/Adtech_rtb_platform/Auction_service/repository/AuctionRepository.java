package com.company.Adtech_rtb_platform.Auction_service.repository;

import com.company.Adtech_rtb_platform.Auction_service.entities.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction,Long> {
}
