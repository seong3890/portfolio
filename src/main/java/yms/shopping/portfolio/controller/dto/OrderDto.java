package yms.shopping.portfolio.controller.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.ToString;
import org.w3c.dom.stylesheets.LinkStyle;
import yms.shopping.portfolio.domain.order.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
public class OrderDto {
    private Long id;
    private String nickname;

    private List<OrderInfoDto> orderInfoDtoList;
    private OrderStatus orderStatus;
    private LocalDateTime createDate;




    @QueryProjection
    public OrderDto(Long id,String nickname, OrderStatus orderStatus, LocalDateTime createDate) {
        this.id = id;
        this.nickname = nickname;
        this.orderStatus = orderStatus;
        this.createDate = createDate;
    }
}
