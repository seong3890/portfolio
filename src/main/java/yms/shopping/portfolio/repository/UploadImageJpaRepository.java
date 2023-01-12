package yms.shopping.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yms.shopping.portfolio.domain.item.UploadImage;

public interface UploadImageJpaRepository extends JpaRepository<UploadImage, Long> {

}
