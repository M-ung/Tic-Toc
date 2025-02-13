package tictoc.auction.port.out;

import org.springframework.data.domain.Pageable;
import tictoc.auction.dto.request.AuctionUseCaseReqDTO;
import tictoc.auction.dto.response.AuctionUseCaseResDTO;
import tictoc.auction.model.Auction;
import tictoc.model.page.PageCustom;
import java.time.LocalDateTime;

public interface AuctionRepositoryPort {
    Auction saveAuction(Auction auction);
    Auction findAuctionByIdOrThrow(Long auctionId);
    void validateAuctionTimeRange(Long userId, LocalDateTime sellStartTime, LocalDateTime sellEndTime);
    PageCustom<AuctionUseCaseResDTO.Auction> findAuctionsByFilterWithPageable(AuctionUseCaseReqDTO.Filter requestDTO, Pageable pageable);
}