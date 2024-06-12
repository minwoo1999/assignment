package com.example.flowglobalassignment.global.init;

import com.example.flowglobalassignment.ip.domain.IpRule;
import com.example.flowglobalassignment.ip.domain.repository.IpRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataGenerator implements CommandLineRunner {

    private final IpRuleRepository ipRuleRepository;

    @Autowired
    public DataGenerator(IpRuleRepository ipRuleRepository) {
        this.ipRuleRepository = ipRuleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 100만 건의 데이터 생성
        List<IpRule> ipRules = generateIpRuleData(1000000);

        // 생성된 데이터를 데이터베이스에 저장
        ipRuleRepository.saveAll(ipRules);
    }

    private List<IpRule> generateIpRuleData(int count) {
        List<IpRule> ipRules = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            // 각각 다른 데이터로 생성
            IpRule ipRule = IpRule.builder()
                    .ipAddress("IP_" + i)
                    .description("Description_" + i)
                    .startTime(LocalDateTime.now())
                    .endTime(LocalDateTime.now().plusDays(1))
                    .build();
            ipRules.add(ipRule);
        }
        return ipRules;
    }
}
