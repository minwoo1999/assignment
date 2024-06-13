package com.example.flowglobalassignment.ip.domain.repository.querydsl;

import com.example.flowglobalassignment.ip.application.dto.IpRuleSearchCondition;
import com.example.flowglobalassignment.ip.application.dto.response.IpRuleListResponseDto;
import com.example.flowglobalassignment.ip.domain.IpRule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface IpRuleQuerydslRepository {

    Slice<IpRuleListResponseDto> readIpRuleList(Pageable pageable, IpRuleSearchCondition ipRuleSearchCondition);

}
