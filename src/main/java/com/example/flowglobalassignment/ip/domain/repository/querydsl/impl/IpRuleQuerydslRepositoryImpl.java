package com.example.flowglobalassignment.ip.domain.repository.querydsl.impl;

import com.example.flowglobalassignment.ip.application.dto.IpRuleSearchCondition;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.flowglobalassignment.ip.domain.QIpRule.*;

@RequiredArgsConstructor
public class IpRuleQuerydslRepositoryImpl implements IpRuleQuerydslRepository {

    private final EntityManager entityManager;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");



    @Override
    public Page<IpRule> readIpRuleList(Pageable pageable, IpRuleSearchCondition ipRuleSearchCondition) {
        System.out.println(ipRuleSearchCondition.getSearchWord());
        JPAQuery<IpRule> contentQuery = new JPAQueryFactory(entityManager).
                selectFrom(ipRule)
                .where(
                        searchWordExpression(ipRuleSearchCondition.getSearchWord()),
                        timeBetween(ipRuleSearchCondition.getStartTime(), ipRuleSearchCondition.getEndTime())
                )
                .orderBy(getOrderSpecifier(pageable.getSort()).stream().toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        JPAQuery<Long> countQuery = new JPAQueryFactory(entityManager)
                .select(ipRule.count())
                .from(ipRule)
                .where(
                        searchWordExpression(ipRuleSearchCondition.getSearchWord()),
                        timeBetween(ipRuleSearchCondition.getStartTime(), ipRuleSearchCondition.getEndTime())
                );

        return PageableExecutionUtils.getPage(contentQuery.fetch(),pageable, () -> countQuery.fetch().size());
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
