package yms.shopping.portfolio.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import yms.shopping.portfolio.domain.Member;

import java.util.Optional;


public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    boolean existsByUsername(String username);

    /**
     * 로그인 아이디 비번 확인
     */
    Optional<Member> findByUsernameAndPassword(String username, String password);

    /**
     * 소셜 반환값중 이미 생성된 사용자인지 확인하기 위한 메소드
     */
//    Optional<Member> findByEmail(String email);

   // Page<Member> findAll(Pageable pageable);


}
