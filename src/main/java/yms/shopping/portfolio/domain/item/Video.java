package yms.shopping.portfolio.domain.item;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("video")
@Getter
@Setter
public class Video extends Items {

    private String producer;
    private String actor;

}
