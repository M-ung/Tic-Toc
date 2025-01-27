package org.tictoc.tictoc.domain.auction.repository.auction;

import org.springframework.data.domain.Pageable;
import org.tictoc.tictoc.domain.auction.dto.auction.request.AuctionRequestDTO;
import org.tictoc.tictoc.domain.auction.dto.auction.response.AuctionResponseDTO;
import org.tictoc.tictoc.global.common.entity.page.PageCustom;


public interface AuctionRepositoryCustom {
    PageCustom<AuctionResponseDTO.Auction> findAuctionsByFilterWithPageable(AuctionRequestDTO.Filter requestDTO, Pageable pageable);
}