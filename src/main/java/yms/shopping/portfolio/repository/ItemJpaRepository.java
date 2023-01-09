package yms.shopping.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yms.shopping.portfolio.domain.item.Items;

public interface ItemJpaRepository extends JpaRepository<Items, Long> {

}
