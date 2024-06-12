package com.example.flowglobalassignment.ip.application.impl;

import com.example.flowglobalassignment.global.exception.error.IpRuleMaximumException;
import com.example.flowglobalassignment.ip.application.IpRuleService;
import com.example.flowglobalassignment.ip.application.dto.IpRuleSearchCondition;
import com.example.flowglobalassignment.ip.application.dto.request.IpRuleRequestDto;
import com.example.flowglobalassignment.ip.application.dto.response.CreateIpRoleResponseDto;
import com.example.flowglobalassignment.ip.application.dto.response.IpRuleListResponseDto;
import com.example.flowglobalassignment.ip.domain.IpRule;
import com.example.flowglobalassignment.ip.domain.repository.IpRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IpRuleServiceImpl implements IpRuleService {

    private final IpRuleRepository ipRuleRepository;
    @Override
    @Transactional
    public CreateIpRoleResponseDto createIpRole(IpRuleRequestDto ipRuleRequestDto) {
        List<IpRule> ipRuleList = ipRuleRepository.findAllByIpAddress(ipRuleRequestDto.getIp());

        ipRuleSizeCheck(ipRuleList);

        IpRule ipRole = ipRuleRequestDto.toEntity();
        IpRule savedIpRule = ipRuleRepository.save(ipRole);

        return CreateIpRoleResponseDto.builder()
                .ip(savedIpRule.getIpAddress())
                .description(savedIpRule.getDescription())
                .startTime(savedIpRule.getStartTime())
                .endTime(savedIpRule.getEndTime())
                .createdAt(LocalDateTime.now())
                .build();
    }

    private static void ipRuleSizeCheck(List<IpRule> ipRuleList) {
        if (ipRuleList.size() >= 50) {
            throw new IpRuleMaximumException("데이터 수가 50개 이상입니다.");
        }
    }

    @Override
    @Transactional
    public boolean deleteIpRole(Long IpRoleId) {

        IpRule ipRole = ipRuleRepository.findById(IpRoleId).orElseThrow(() -> new IllegalArgumentException("해당 아이피 규칙이 존재하지 않습니다."));
        ipRole.deleteIpRole();
        return true;
    }

    @Override
    public Page<IpRuleListResponseDto> readIpRole(IpRuleSearchCondition ipRuleSearchCondition, Pageable pageable) {
        Page<IpRule> ipRules = ipRuleRepository.readIpRuleList(pageable, ipRuleSearchCondition);
        List<IpRuleListResponseDto> dtoList = ipRules.stream()
                .map(board -> new IpRuleListResponseDto(board))
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, ipRules.getPageable(), ipRules.getTotalElements());

    }
}
