package yms.shopping.portfolio.domain.item;

import lombok.Getter;
import lombok.Setter;
import yms.shopping.portfolio.domain.Member;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "items")
    private List<UploadImage> uploadImage=new ArrayList<>();



    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void removeQuantity(int quantity) {
        if (this.quantity - quantity < 0) {
            throw new RuntimeException("수량이 부족합니다");
        }
        this.quantity = this.quantity - quantity;
    }
}
