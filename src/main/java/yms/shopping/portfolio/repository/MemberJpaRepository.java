package yms.shopping.portfolio.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import yms.shopping.portfolio.domain.Member;

import java.util.Optional;


public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    boolean existsByUsername(String username);

    Optional<Member> findByUsernameAndPassword(String username, String password);

    Page<Member> findAll(Pageable pageable);


}
