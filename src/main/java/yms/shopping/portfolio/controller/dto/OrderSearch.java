package yms.shopping.portfolio.controller.dto;

import lombok.Data;
import yms.shopping.portfolio.domain.order.OrderStatus;

@Data
public class OrderSearch {
    private String memberName;
    private OrderStatus orderStatus;

    public OrderSearch(String memberName, OrderStatus orderStatus) {
        this.memberName = memberName;
        this.orderStatus = orderStatus;
    }
}
