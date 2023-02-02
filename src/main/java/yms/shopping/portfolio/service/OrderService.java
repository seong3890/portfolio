package yms.shopping.portfolio.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yms.shopping.portfolio.controller.dto.OrderDto;
import yms.shopping.portfolio.controller.dto.OrderInfoDto;
import yms.shopping.portfolio.controller.dto.OrderSearch;
import yms.shopping.portfolio.domain.Member;
import yms.shopping.portfolio.domain.item.Items;
import yms.shopping.portfolio.domain.order.Delivery;
import yms.shopping.portfolio.domain.order.DeliveryStatus;
import yms.shopping.portfolio.domain.order.Order;
import yms.shopping.portfolio.domain.order.OrderInfo;
import yms.shopping.portfolio.repository.ItemJpaRepository;
import yms.shopping.portfolio.repository.MemberJpaRepository;
import yms.shopping.portfolio.repository.OrderJpaRepository;
import yms.shopping.portfolio.repository.query.OrderQueryRepository;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class OrderService {
    private final OrderJpaRepository orderJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final ItemJpaRepository itemJpaRepository;
    private final OrderQueryRepository orderQueryRepository;

    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        Member member = memberJpaRepository.findById(memberId).orElseThrow();
        Items items = itemJpaRepository.findById(itemId).orElseThrow();
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setDeliveryStatus(DeliveryStatus.READY);
        OrderInfo orderInfo = OrderInfo.createOrderInfo(items, items.getPrice(), count);
        Order order = Order.createOrder(member, delivery, orderInfo);
        orderJpaRepository.save(order);
        return order.getId();
    }

    public Page<OrderDto> findPageList(OrderSearch search, Pageable pageable) {
        Page<OrderDto> orderDtos = orderQueryRepository.searchPage(search, pageable);


        return orderDtos;
    }

        @Transactional
        public void cancelOrder(Long orderId) {
            //주문 엔티티 조회
            Order order = orderJpaRepository.findById(orderId).orElseThrow();
            //주문 취소
            order.cancel();
        }

}
