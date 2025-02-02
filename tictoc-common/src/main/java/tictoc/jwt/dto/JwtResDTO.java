package tictoc.jwt.dto;

import tictoc.jwt.RefreshToken;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class JwtResDTO {
    public record Login(
            Long userId,
            String accessToken,
            String refreshToken,
            Long accessExpiredTime,
            Long refreshExpiredTime
    ){
        public static Login of(final JwtResDTO.AccessToken accessToken, final RefreshToken refreshToken) {
            return new Login(
                    refreshToken.getUserId(),
                    accessToken.accessToken,
                    refreshToken.getToken(),
                    accessToken.accessExpiredTime,
                    refreshToken.getExpiredAt().atZone(ZoneId.systemDefault()).toEpochSecond()
            );
        }
    }

    public record AccessToken(
            Long userId,
            String accessToken,
            Long accessExpiredTime
    ) {
        public static AccessToken of(final Long userId, final String accessToken, final LocalDateTime expiredAt) {
            return new AccessToken(
                    userId,
                    accessToken,
                    expiredAt.atZone(ZoneId.systemDefault()).toEpochSecond()
            );
        }
    }
}