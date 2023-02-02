package yms.shopping.portfolio.domain.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("book")
@Getter
@Setter
public class Book extends Items {
    private String writer;
    private String isbn;


}
