package yms.shopping.portfolio.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Getter
@Setter
public abstract class Items {

    @Id
    @GeneratedValue
    @Column(name = "items_id")
    private Long id;

    private String name;
    private String creator;
    private int price;
}
