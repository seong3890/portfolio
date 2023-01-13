package yms.shopping.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import yms.shopping.portfolio.domain.item.Items;

import java.util.List;

public interface ItemJpaRepository extends JpaRepository<Items, Long> {

    @Query("select distinct i from Items i join fetch i.uploadImage u")
    List<Items> findItemsAndUpload();
}
