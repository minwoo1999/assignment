package com.example.flowglobalassignment.ip.application.dto.response;

import com.example.flowglobalassignment.ip.domain.IpRule;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Getter
public class IpRuleListResponseDto {
    private Long ip;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createdAt;

    public IpRuleListResponseDto(IpRule ipRule) {
        this.ip = ipRule.getId();
        this.description = ipRule.getDescription();
        this.startTime = ipRule.getStartTime();
        this.endTime = ipRule.getEndTime();
        this.createdAt = ipRule.getCreatedAt();
    }
}
