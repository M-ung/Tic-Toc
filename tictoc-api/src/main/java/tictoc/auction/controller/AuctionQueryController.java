package tictoc.auction.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tictoc.annotation.UserId;
import tictoc.auction.dto.request.AuctionReqDTO;
import tictoc.auction.dto.response.AuctionResDTO;
import tictoc.auction.mapper.AuctionReqMapper;
import tictoc.auction.mapper.AuctionResMapper;
import tictoc.auction.port.AuctionQueryUseCase;
import tictoc.model.page.PageCustom;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member/auction")
public class AuctionQueryController {
    private final AuctionReqMapper auctionReqMapper;
    private final AuctionResMapper auctionResMapper;
    private final AuctionQueryUseCase auctionQueryUseCase;

    @GetMapping("")
    @Operation(summary = "경매 필터링 조회 API", description = "경매 필터링 조회 API 입니다.")
    public ResponseEntity<PageCustom<AuctionResDTO.Auction>> getAuctions (@RequestBody @Valid AuctionReqDTO.Filter requestDTO, Pageable pageable) {
        return ResponseEntity.ok().body(auctionResMapper.toAuctionPage(auctionQueryUseCase.getAuctionsByFilter(auctionReqMapper.toUseCaseDTO(requestDTO), pageable)));
    }

    @GetMapping("/{auctionId}/detail")
    @Operation(summary = "경매 상세 조회 API", description = "경매 상세 조회 API 입니다.")
    public ResponseEntity<AuctionResDTO.Detail> getDetail (@PathVariable("auctionId") Long auctionId) {
        return ResponseEntity.ok().body(auctionResMapper.toDetail(auctionQueryUseCase.getDetail(auctionId)));
    }

    @GetMapping("/my")
    @Operation(summary = "내 경매 목록 조회 API", description = "내 경매 목록 조회 API 입니다.")
    public ResponseEntity<PageCustom<AuctionResDTO.Auction>> getMyAuctions (@UserId final Long userId, Pageable pageable) {
        return ResponseEntity.ok().body(auctionResMapper.toAuctionPage(auctionQueryUseCase.getMyAuctionsByUserId(userId, pageable)));
    }
}