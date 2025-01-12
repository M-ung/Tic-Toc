package org.tictoc.tictoc.domain.auction.controller.command;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tictoc.tictoc.domain.auction.dto.request.AuctionRequestDTO;
import org.tictoc.tictoc.domain.auction.service.command.AuctionCommandService;
import org.tictoc.tictoc.global.auth.resolver.UserId;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member/auction")
public class AuctionCommandController {
    private final AuctionCommandService auctionCommandService;

    @PostMapping("/register")
    @Operation(summary = "로그인 API", description = "로그인 API 입니다.")
    public ResponseEntity<String> register (@UserId final Long userId, @RequestBody AuctionRequestDTO.Register requestDTO) {
        auctionCommandService.register(userId, requestDTO);
        return ResponseEntity.ok().body("경매가 등록되었습니다.");
    }

    @PostMapping("/update")
    @Operation(summary = "로그인 API", description = "로그인 API 입니다.")
    public ResponseEntity<String> update (@UserId final Long userId, @RequestBody AuctionRequestDTO.Update requestDTO) {
        auctionCommandService.update(userId, requestDTO);
        return ResponseEntity.ok().body("경매가 수정되었습니다.");
    }

    @PostMapping("/delete/{auctionId}")
    @Operation(summary = "로그인 API", description = "로그인 API 입니다.")
    public ResponseEntity<String> delete (@UserId final Long userId, @PathVariable("auctionId") Long auctionId) {
        auctionCommandService.delete(userId, auctionId);
        return ResponseEntity.ok().body("경매가 삭제되었습니다.");
    }
}
