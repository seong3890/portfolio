package yms.shopping.portfolio.domain.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import yms.shopping.portfolio.domain.item.Items;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor
public class orderInfo {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Items items;
}
