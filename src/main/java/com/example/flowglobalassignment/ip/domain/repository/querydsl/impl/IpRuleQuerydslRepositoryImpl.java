package com.example.flowglobalassignment.ip.domain.repository.querydsl.impl;

import com.example.flowglobalassignment.ip.application.dto.IpRuleSearchCondition;
import com.example.flowglobalassignment.ip.application.dto.response.IpRuleListResponseDto;
import com.example.flowglobalassignment.ip.domain.IpRule;
import com.example.flowglobalassignment.ip.domain.repository.querydsl.IpRuleQuerydslRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.*;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.flowglobalassignment.ip.domain.QIpRule.*;

@RequiredArgsConstructor
public class IpRuleQuerydslRepositoryImpl implements IpRuleQuerydslRepository {

    private final EntityManager entityManager;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");



    @Override
    public Slice<IpRuleListResponseDto> readIpRuleList(Pageable pageable, IpRuleSearchCondition ipRuleSearchCondition) {
        List<IpRule> ipRuleList = new JPAQueryFactory(entityManager)
                .selectFrom(ipRule)
                .where(
                        searchWordExpression(ipRuleSearchCondition.getSearchWord()),
                        timeBetween(ipRuleSearchCondition.getStartTime(), ipRuleSearchCondition.getEndTime())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = false;
        if (ipRuleList.size() > pageable.getPageSize()) {
            ipRuleList.remove(ipRuleList.size() - 1);
            hasNext = true;
        }

        List<IpRuleListResponseDto> content = ipRuleList.stream()
                .map(ipRule -> new IpRuleListResponseDto(ipRule)) // Assuming you have a constructor or method to map IpRule to IpRuleListResponseDto
                .collect(Collectors.toList());

        return new SliceImpl<>(content, pageable, hasNext);
    }

    /**
     * 시작 시간 이후의 레코드와 끝 시간 이전의 레코드를 검색
     */
    private BooleanBuilder timeBetween(LocalDateTime startTime, LocalDateTime endTime) {

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (startTime != null) {
            booleanBuilder.and(ipRule.startTime.goe(startTime));
        }
        if (endTime != null) {
            booleanBuilder.and(ipRule.endTime.loe(endTime));
        }
        return booleanBuilder;
    }


    /**
     * 검색어 단어를 description 동적검색
     */
    private BooleanExpression searchWordExpression(String searchWord) {
        return Optional.ofNullable(searchWord)
                .filter(word -> !word.isEmpty())
                .map(word -> {
                    String keyword = "%" + word.toLowerCase() + "%";
                    return ipRule.description.lower().like(keyword);
                })
                .orElse(null);
    }




    /**
     * 동적 orderby
     */

    private List<OrderSpecifier> getOrderSpecifier(Sort sort){
        List<OrderSpecifier> orders=new ArrayList<>();

        sort.stream().forEach(order->{
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String property = order.getProperty();
            PathBuilder<IpRule> orderByExpression = new PathBuilder<>(IpRule.class, "ipRule");
            orders.add(new OrderSpecifier(direction,orderByExpression.get(property)));

        });
        return orders;
    }
}
