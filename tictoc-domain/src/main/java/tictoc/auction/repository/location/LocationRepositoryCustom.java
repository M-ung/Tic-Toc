package tictoc.auction.repository.location;

import tictoc.auction.dto.request.AuctionUseCaseReqDTO;
import java.util.Optional;

public interface LocationRepositoryCustom {
    Optional<Long> findLocationIdByFilter(AuctionUseCaseReqDTO.Location requestDTO);
}