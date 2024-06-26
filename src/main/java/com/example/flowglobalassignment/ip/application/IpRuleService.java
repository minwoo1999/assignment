package com.example.flowglobalassignment.ip.application;

import com.example.flowglobalassignment.ip.application.dto.IpRuleSearchCondition;
import com.example.flowglobalassignment.ip.application.dto.request.IpRuleRequestDto;
import com.example.flowglobalassignment.ip.application.dto.response.CreateIpRoleResponseDto;
import com.example.flowglobalassignment.ip.application.dto.response.IpRuleListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface IpRuleService {
    CreateIpRoleResponseDto createIpRole(IpRuleRequestDto ipRuleRequestDto);
    boolean deleteIpRole(Long IpRoleId);

    Slice<IpRuleListResponseDto> readIpRole(IpRuleSearchCondition ipRuleSearchCondition, Pageable pageable);

    Integer  getIpRoleCount(String ipAddress);
}
