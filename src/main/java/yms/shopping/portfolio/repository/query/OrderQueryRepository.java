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
import yms.shopping.portfolio.repository.ItemJpaRepository;
import yms.shopping.portfolio.repository.OrderJpaRepository;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.*;
import static yms.shopping.portfolio.domain.QMember.member;
import static yms.shopping.portfolio.domain.item.QItems.*;
import static yms.shopping.portfolio.domain.order.QOrder.order;
import static yms.shopping.portfolio.domain.order.QOrderInfo.orderInfo;

@Repository
@Slf4j
public class OrderQueryRepository {
    private final ItemJpaRepository itemJpaRepository;
    private final OrderJpaRepository orderJpaRepository;
    private final EntityManager em;

    private JPAQueryFactory factory;

    public OrderQueryRepository(EntityManager em,
                                OrderJpaRepository orderJpaRepository,
                                ItemJpaRepository itemJpaRepository) {
        this.em = em;
        this.factory = new JPAQueryFactory(em);
        this.orderJpaRepository = orderJpaRepository;
        this.itemJpaRepository = itemJpaRepository;
    }

    /**
     * 인프런 JPA 쿼리 최적화 참고
     */
    public Page<OrderDto> searchPage(OrderSearch search, Pageable pageable) {
        // 다대일 관계 페이징 처리
        List<OrderDto> fetch = factory.select(new QOrderDto(order.id,member.nickname, order.orderStatus, order.createDate)
                ).from(order)
                .join(order.member, member)
                .where(memberEq(search.getMemberName()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 일대다 OrderInfo를 Map으로 변환 (in 연산자로 최적화)
        Map<Long, List<OrderInfoDto>> orderInfoMap = findOrderInfoMap(OrderIds(fetch));
        //fetch에 orderInfo값 넣기
        fetch.forEach(o -> o.setOrderInfoDtoList(orderInfoMap.get(o.getId())));
     /*   fetch.forEach(orderDto -> {
            List<OrderInfoDto> orderInfos = findOrderInfos(orderDto.getId());
            orderDto.setOrderInfoDtoList(orderInfos);
        });*/

        JPAQuery<Long> where = factory.select(order.count())
                .from(order)
                .join(order.member, member)
                .where(memberEq(search.getMemberName()));

        return PageableExecutionUtils.getPage(fetch, pageable, () -> where.fetchOne());
    }

    public Map<Long, List<OrderInfoDto>> findOrderInfoMap(List<Long> orderIds) {
        List<OrderInfoDto> fetch = factory.select(new QOrderInfoDto(orderInfo.order.id, items.name, orderInfo.orderPrice, orderInfo.count))
                .from(orderInfo)
                .join(orderInfo.items,items)
                .where(orderInfo.order.id.in(orderIds))
                .fetch();

        Map<Long, List<OrderInfoDto>> collect = fetch.stream().collect(Collectors.groupingBy(o -> o.getId()));
        return collect;
    }

    public List<Long> OrderIds(List<OrderDto> orderDtos) {
        List<Long> collect = orderDtos.stream().map(o -> o.getId()).collect(Collectors.toList());
        return collect;
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
