package tictoc.auction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tictoc.auction.dto.request.AuctionUseCaseReqDTO;
import tictoc.auction.dto.response.AuctionUseCaseResDTO;
import tictoc.auction.port.in.AuctionQueryUseCase;
import tictoc.auction.port.out.AuctionRepositoryPort;
import tictoc.model.page.PageCustom;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuctionQueryService implements AuctionQueryUseCase {
    private final AuctionRepositoryPort auctionRepositoryPort;

    @Override
    public PageCustom<AuctionUseCaseResDTO.Auction> getAuctionsByFilter(AuctionUseCaseReqDTO.Filter requestDTO, Pageable pageable) {
        return auctionRepositoryPort.findAuctionsByFilterWithPageable(requestDTO, pageable);
    }
}