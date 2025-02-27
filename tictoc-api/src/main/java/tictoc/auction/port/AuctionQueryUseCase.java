package tictoc.auction.port;

import org.springframework.data.domain.Pageable;
import tictoc.auction.dto.request.AuctionUseCaseReqDTO;
import tictoc.auction.dto.response.AuctionUseCaseResDTO;
import tictoc.model.page.PageCustom;

public interface AuctionQueryUseCase {
    PageCustom<AuctionUseCaseResDTO.Auction> getAuctionsByFilter(AuctionUseCaseReqDTO.Filter requestDTO, Pageable pageable);
    AuctionUseCaseResDTO.Detail getDetail(Long auctionId);
    PageCustom<AuctionUseCaseResDTO.Auction> getMyAuctionsByUserId(final Long userId, Pageable pageable);
}
