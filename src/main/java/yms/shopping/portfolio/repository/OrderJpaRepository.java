package yms.shopping.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yms.shopping.portfolio.domain.order.Order;

public interface OrderJpaRepository extends JpaRepository<Order,Long> {
}
