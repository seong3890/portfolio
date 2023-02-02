package yms.shopping.portfolio.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import yms.shopping.portfolio.domain.item.Book;
import yms.shopping.portfolio.domain.item.Items;

import java.util.List;
import java.util.Optional;

public interface ItemJpaRepository extends JpaRepository<Items, Long> {

    @Query(value = "select distinct i from Items i join fetch i.uploadImage u join fetch i.member m",
    countQuery = "select count(i) from Items i")
    Page<Items> findItemsAndUpload(Pageable pageable);

    @Query("select distinct i from Items i join fetch i.uploadImage u join fetch i.member m where i.id= :id")
    Optional<Items> findItemAndUpload(@Param("id") Long id);

    @Query("select b from Book b where b.id= :itemId")
    Optional<Book> findBook(@Param("itemId") Long itemId);
}
