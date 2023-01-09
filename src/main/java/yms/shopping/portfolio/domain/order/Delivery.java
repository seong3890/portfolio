package yms.shopping.portfolio.domain.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yms.shopping.portfolio.domain.Address;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private Address address;

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    public Delivery(Address address) {
        this.address = address;
    }
}
