package com.example.flowglobalassignment.ip.domain.repository;

import com.example.flowglobalassignment.ip.domain.IpRule;
import com.example.flowglobalassignment.ip.domain.repository.querydsl.IpRuleQuerydslRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IpRuleRepository extends JpaRepository<IpRule, Long> , IpRuleQuerydslRepository {
    List<IpRule> findAllByIpAddress(String ipAddress);
}
