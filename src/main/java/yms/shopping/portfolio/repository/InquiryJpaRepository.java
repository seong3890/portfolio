package yms.shopping.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yms.shopping.portfolio.domain.board.Inquiry;

public interface InquiryJpaRepository extends JpaRepository<Inquiry, Long> {

}
