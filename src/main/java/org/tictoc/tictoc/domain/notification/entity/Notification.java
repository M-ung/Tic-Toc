package org.tictoc.tictoc.domain.notification.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.tictoc.tictoc.domain.notification.entity.type.NotificationStatus;
import org.tictoc.tictoc.global.common.entity.BaseTimeEntity;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String message;
    @Enumerated(EnumType.STRING)
    private NotificationStatus status;
    private Boolean isRead = false;
}
