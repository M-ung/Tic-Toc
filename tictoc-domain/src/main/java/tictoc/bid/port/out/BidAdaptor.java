package tictoc.bid.port.out;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import tictoc.auction.model.Auction;
import tictoc.bid.dto.request.BidUseCaseReqDTO;
import tictoc.bid.dto.response.BidUseCaseResDTO;
import tictoc.bid.model.Bid;
import tictoc.bid.repository.BidRepository;
import tictoc.auction.model.type.AuctionProgress;
import tictoc.model.page.PageCustom;

@Component
@RequiredArgsConstructor
public class BidAdaptor {
    private final BidRepository bidRepository;

    public PageCustom<BidUseCaseResDTO.Bid> findBidsByFilterWithPageable(Long userId, BidUseCaseReqDTO.Filter requestDTO, Pageable pageable) {
        return bidRepository.findBidsByFilterWithPageable(userId, requestDTO, pageable);
    }

    public void checkBeforeBid(Auction auction) {
        if (auction.getProgress().equals(AuctionProgress.IN_PROGRESS)) {
            bidRepository.findByAuctionIdAndStatusOrThrow(auction.getId()).fail();
        }
    }
    public void save(Bid bid) {
        bidRepository.save(bid);
    }
}
