package yms.shopping.portfolio.domain.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yms.shopping.portfolio.domain.item.Items;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderInfo {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Items items;

    private int orderPrice;

    private int count;

    //==생성 메서드==//
    public static OrderInfo createOrderInfo(Items items, int orderPrice, int count) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setItems(items);
        orderInfo.setOrderPrice(orderPrice);
        orderInfo.setCount(count);

        items.removeQuantity(count);
        return orderInfo;
    }

    //==비즈니스 로직==//
    public void cancel() {
        getItems().addQuantity(count);
    }
    //==조회 로직==//

    /**
     * 주문 상품 전체 가격 조회
     */

    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
