package tictoc.user.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;

@Slf4j
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_login_history")
public class UserLoginHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private LocalDateTime loginAt;
    private String ipAddress;
    private String device;

    public static UserLoginHistory of(Long userId, String ipAddress, String device) {
        log.info("[Login History] UserId: {}, IPAddress: {}, Device: {}", userId, ipAddress, device);
        return UserLoginHistory.builder()
                .userId(userId)
                .loginAt(LocalDateTime.now())
                .ipAddress(ipAddress)
                .device(device)
                .build();
    }
}