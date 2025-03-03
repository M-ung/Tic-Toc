package tictoc.bid.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tictoc.auction.port.AuctionRepositoryPort;
import tictoc.bid.dto.request.BidUseCaseReqDTO;
import tictoc.bid.exception.BidException;
import tictoc.bid.model.Bid;
import tictoc.bid.port.BidCommandUseCase;
import tictoc.bid.port.BidRepositoryPort;
import static tictoc.error.ErrorCode.BID_FAIL;

@Service
@Transactional
@RequiredArgsConstructor
public class BidCommandService implements BidCommandUseCase {
    private final AuctionRepositoryPort auctionRepositoryPort;
    private final BidRepositoryPort bidRepositoryPort;

    @Override
    public void bid(final Long userId, BidUseCaseReqDTO.Bid requestDTO) {
        if(auctionRepositoryPort.updateBidIfHigher(requestDTO) == 0) {
            throw new BidException(BID_FAIL);
        }
        var findAuction = auctionRepositoryPort.findAuctionById(requestDTO.auctionId());
        bidRepositoryPort.checkBeforeBid(findAuction);
        findAuction.start(userId);
        bidRepositoryPort.saveBid(Bid.of(userId, requestDTO));
    }
}