package yms.shopping.portfolio.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import yms.shopping.portfolio.domain.Member;
import yms.shopping.portfolio.domain.board.Inquiry;
import yms.shopping.portfolio.domain.board.Post;

import java.util.List;

public interface PostJpaRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p join fetch p.member m join fetch p.inquiry i where p.id=m.id and p.id=i.id")
    Post findPostJoinMembersAndInquiry();

    @Query("select p from Post p join fetch p.member m")
    List<Post> findPostJoinMembers();

    @Query(value = "select p from Post p join fetch p.member m",
            countQuery = "select count (p) from Post p")
    Page<Post> findPostJoinMembersPage(Pageable pageable);

    @Query("select p from Post p")
    List<Post> findalll();

    List<Post> findPostByMemberAndInquiry(Member member, Inquiry inquiry);
}
