package com.example.flowglobalassignment.ip.application.dto.request;

import com.example.flowglobalassignment.ip.domain.IpRule;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
@Builder
public class IpRuleRequestDto {


    @NotEmpty(message = "설명은 필수입니다")
    @Size(min = 1, max = 20, message = "설명은 20자 이하로 입력해주세요.")
    private String description;

    @NotEmpty(message = "아이피 입력 값은 필수입니다.")
    private String ip;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime endTime;

    public IpRule toEntity() {
        return IpRule.builder()
                .ipAddress(ip)
                .description(description)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }
}