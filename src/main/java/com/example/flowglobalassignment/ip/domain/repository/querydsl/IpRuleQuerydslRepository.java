package com.example.flowglobalassignment.ip.domain.repository.querydsl;

import com.example.flowglobalassignment.ip.application.dto.IpRuleSearchCondition;
import com.example.flowglobalassignment.ip.domain.IpRule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IpRuleQuerydslRepository {

    Page<IpRule> readIpRuleList(Pageable pageable, IpRuleSearchCondition ipRuleSearchCondition);

}
