package yms.shopping.portfolio.repository.query;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import yms.shopping.portfolio.controller.dto.*;
import yms.shopping.portfolio.domain.QMember;
import yms.shopping.portfolio.domain.item.Items;
import yms.shopping.portfolio.domain.item.QItems;
import yms.shopping.portfolio.domain.order.OrderStatus;
import yms.shopping.portfolio.domain.order.QOrder;
import yms.shopping.portfolio.domain.order.QOrderInfo;

import javax.persistence.EntityManager;

import java.util.List;

import static org.springframework.util.StringUtils.*;
import static yms.shopping.portfolio.domain.QMember.member;
import static yms.shopping.portfolio.domain.item.QItems.*;
import static yms.shopping.portfolio.domain.order.QOrder.order;
import static yms.shopping.portfolio.domain.order.QOrderInfo.orderInfo;

@Repository
@Slf4j
public class OrderQueryRepository {
    private final EntityManager em;

    private JPAQueryFactory factory;

    public OrderQueryRepository(EntityManager em) {
        this.em = em;
        this.factory = new JPAQueryFactory(em);
    }

    public Page<OrderDto> searchPage(OrderSearch search, Pageable pageable) {
        List<OrderDto> fetch = factory.select(new QOrderDto(order.id,member.nickname, order.orderStatus, order.createDate)
                ).from(order)
                .join(order.member, member)
                .where(memberEq(search.getMemberName()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        fetch.forEach(orderDto -> {
            List<OrderInfoDto> orderInfos = findOrderInfos(orderDto.getId());
            orderDto.setOrderInfoDtoList(orderInfos);
        });

        JPAQuery<Long> where = factory.select(order.count())
                .from(order)
                .join(order.member, member)
                .where(memberEq(search.getMemberName()));
        return PageableExecutionUtils.getPage(fetch, pageable, () -> where.fetchOne());
    }

    private List<OrderInfoDto> findOrderInfos(Long orderId) {
        List<OrderInfoDto> fetch = factory.select(new QOrderInfoDto(items.id, items.name, orderInfo.orderPrice, orderInfo.count))
                .from(orderInfo)
                .join(orderInfo.items, items)
                .where(orderInfo.order.id.eq(orderId))
                .fetch();
        return fetch;
    }

    private BooleanExpression orderEq(String orderStatus) {
        return hasText(orderStatus) ? order.orderStatus.eq(OrderStatus.valueOf(orderStatus)) : null;

    }

    private BooleanExpression memberEq(String memberName) {
        return hasText(memberName) ? member.nickname.eq(memberName) : null;

    }
}
