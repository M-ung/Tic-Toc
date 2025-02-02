package tictoc.jwt.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tictoc.jwt.dto.JwtResDTO;
import tictoc.error.ErrorCode;

@RequiredArgsConstructor
@Component
public class JwtProvider {
    private final JwtGenerator jwtGenerator;
    private final RefreshTokenGenerator refreshTokenGenerator;

    public JwtResDTO.Login createJwt(final Long userId) {
        return JwtResDTO.Login.of(
                jwtGenerator.generateAccessToken(userId),
                refreshTokenGenerator.generateRefreshToken(userId)
        );
    }

    public Long getUserIdFromToken(String token) {
        var subject = jwtGenerator.parseToken(token).getBody().getSubject();
        try {
            return Long.parseLong(subject);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.valueOf(ErrorCode.TOKEN_SUBJECT_NOT_NUMERIC_STRING));
        }
    }
}