package org.tictoc.tictoc.domain.auction.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.tictoc.tictoc.domain.auction.dto.request.AuctionRequestDTO;
import org.tictoc.tictoc.domain.auction.dto.response.AuctionResponseDTO;
import org.tictoc.tictoc.domain.auction.entity.Zone;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionProgress;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionType;
import org.tictoc.tictoc.global.common.entity.PageCustom;
import org.tictoc.tictoc.global.common.entity.type.TicTocStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.tictoc.tictoc.domain.auction.entity.QAuction.auction;

public class AuctionRepositoryImpl implements AuctionRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    public AuctionRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public PageCustom<AuctionResponseDTO.Auctions> findAuctionsByFilterWithPageable(AuctionRequestDTO.Filter requestDTO, Pageable pageable) {
        var results = queryFactory.select(Projections.constructor(AuctionResponseDTO.Auctions.class,
                        auction.id,
                        auction.title,
                        auction.startPrice,
                        auction.currentPrice,
                        auction.sellStartTime,
                        auction.sellEndTime,
                        auction.auctionOpenTime,
                        auction.auctionCloseTime,
                        auction.zones,
                        auction.progress,
                        auction.type
                ))
                .from(auction)
                .where(
                        auction.status.eq(TicTocStatus.ACTIVE),
                        filterPrice(requestDTO.startPrice(), requestDTO.endPrice()),
                        filterSellTime(requestDTO.sellStartTime(), requestDTO.sellEndTime()),
                        filterZones(requestDTO.zones()),
                        filterProgress(requestDTO.progress()),
                        filterType(requestDTO.type())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = getTotal();
        int totalPages = (int) Math.ceil((double) total / pageable.getPageSize());

        return new PageCustom<>(results, totalPages, total, pageable.getPageSize(), pageable.getPageNumber());
    }

    private BooleanExpression filterPrice(Integer startPrice, Integer endPrice) {
        return startPrice != null && endPrice != null
                ? auction.currentPrice.between(startPrice, endPrice)
                : null;
    }

    private BooleanExpression filterSellTime(LocalDateTime sellStartTime, LocalDateTime sellEndTime) {
        if (sellStartTime != null && sellEndTime != null) {
            return auction.sellStartTime.goe(sellStartTime).and(auction.sellEndTime.loe(sellEndTime));
        } else if (sellStartTime != null) {
            return auction.sellStartTime.goe(sellStartTime);
        } else if (sellEndTime != null) {
            return auction.sellEndTime.loe(sellEndTime);
        }
        return null;
    }

    private BooleanExpression filterZones(List<Zone> zones) {
        return zones != null && !zones.isEmpty()
                ? auction.zones.any().in(zones)
                : null;
    }

    private BooleanExpression filterProgress(AuctionProgress progress) {
        return progress != null
                ? auction.progress.eq(progress)
                : null;
    }

    private BooleanExpression filterType(AuctionType type) {
        return type != null
                ? auction.type.eq(type)
                : null;
    }

    private long getTotal() {
        return Optional.ofNullable(
                queryFactory.select(auction.count())
                        .from(auction)
                        .where(auction.status.eq(TicTocStatus.ACTIVE))
                        .fetchOne()
        ).orElse(0L);
    }
}
