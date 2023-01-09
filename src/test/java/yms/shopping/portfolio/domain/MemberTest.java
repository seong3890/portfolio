package yms.shopping.portfolio.domain;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import yms.shopping.portfolio.domain.order.Delivery;
import yms.shopping.portfolio.domain.order.Order;
import yms.shopping.portfolio.domain.order.OrderStatus;
import yms.shopping.portfolio.service.MemberServiceImpl;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberTest {
    @Autowired
    EntityManager em;
    @Autowired
    MemberServiceImpl memberService;

    @Test
    void memberInsert() {
        Member member = new Member("asd", "안녕", "asd123", new Address("서울", "경기도"), LocalDateTime.now());
        em.persist(member);

        Delivery delivery = new Delivery(member.getAddress());
        em.persist(delivery);

        Order order = new Order(member, delivery, OrderStatus.ORDER);

        em.persist(order);
    }

    @Test
    void save() {
        Member member = new Member("asd", "안녕", "asd123", new Address("서울", "경기도"), LocalDateTime.now());
        memberService.signUp(member);

    }

}