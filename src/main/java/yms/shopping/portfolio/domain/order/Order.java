package yms.shopping.portfolio.domain.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yms.shopping.portfolio.domain.BaseTimeEntity;
import yms.shopping.portfolio.domain.Member;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    List<OrderInfo> orderInfos = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public Order(Member member, Delivery delivery, OrderStatus orderStatus) {
        this.member = member;
        this.delivery = delivery;
        this.orderStatus = orderStatus;
    }
    public Order(Member member, Delivery delivery) {
        this.member = member;
        this.delivery = delivery;
        delivery.setOrder(this);

    }

    public void addOrderItem(OrderInfo orderInfo) {
        orderInfos.add(orderInfo);
        orderInfo.setOrder(this);
    }

    public static Order createOrder(Member member, Delivery delivery, OrderInfo... orderInfo) {
        Order order = new Order(member,delivery);
        for (OrderInfo info : orderInfo) {
            order.addOrderItem(info);
        }

        order.setOrderStatus(OrderStatus.ORDER);
        return order;
    }

    /**
     * 주문 취소
     */
    public void cancel() {
        if (delivery.getDeliveryStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 주문 완료된 상품입니다.");
        }
        this.setOrderStatus(OrderStatus.CANCEL);
        for (OrderInfo orderInfo : orderInfos) {
            orderInfo.cancel();
        }
    }
}
