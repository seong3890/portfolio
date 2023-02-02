package yms.shopping.portfolio.controller.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import yms.shopping.portfolio.domain.order.OrderInfo;

@Data
public class OrderInfoDto {
    private Long Id; //주문번호
    private String name;//상품 명
    private int orderPrice; //주문 가격
    private int count;      //주문 수량

    @QueryProjection
    public OrderInfoDto(Long id, String name, int orderPrice, int count) {
        Id = id;
        this.name = name;
        this.orderPrice = orderPrice;
        this.count = count;
    }

    public OrderInfoDto(OrderInfo orderInfo) {

    }
}
