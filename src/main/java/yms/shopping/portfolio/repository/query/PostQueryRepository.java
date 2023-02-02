package yms.shopping.portfolio.repository.query;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import yms.shopping.portfolio.controller.dto.BoardPostDto;
import yms.shopping.portfolio.controller.dto.BoardSearch;
import yms.shopping.portfolio.controller.dto.QBoardPostDto;
import yms.shopping.portfolio.controller.dto.UpdateBoardPostDto;

import javax.persistence.EntityManager;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static yms.shopping.portfolio.domain.QMember.member;
import static yms.shopping.portfolio.domain.board.QInquiry.inquiry1;
import static yms.shopping.portfolio.domain.board.QPost.post;

@Repository
public class PostQueryRepository {
    private final EntityManager em;
    private JPAQueryFactory factory;

    public PostQueryRepository(EntityManager em) {
        this.em = em;
        this.factory = new JPAQueryFactory(em);
    }

    public UpdateBoardPostDto findPostAndMemberAndInquiry(Long id) {
        return em.createQuery("select new yms.shopping.portfolio.controller.dto.UpdateBoardPostDto" +
                        "(p.id,p.title,p.write,m.nickname,i.inquiry) from Post p" +
                        " join  p.member m" +
                        " join  p.inquiry i" +
                        " where p.id= :id", UpdateBoardPostDto.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public UpdateBoardPostDto findPostAndMemberAndInquiryV2(Long id) {
    /*    em.createQuery("select new yms.shopping.portfolio.controller.dto.UpdateBoardPostDto" +
                        "(p.id,p.title,p.write,m.nickname,i.inquiry) from Post p" +
                        " join  p.member m" +
                        " join  p.inquiry i" +
                        " where p.id= :id", UpdateBoardPostDto.class)
                .setParameter("id", id)
                .getSingleResult();*/

        return factory.select(Projections.bean(UpdateBoardPostDto.class, post.id, post.title, post.write, member.nickname, inquiry1.inquiry
                        ,inquiry1.id.as("inquiryId"))
                ).from(post)
                .join(post.member, member)
                .join(post.inquiry, inquiry1)
                .where(post.id.eq(id))
                .fetchOne();
    }

    public Page<BoardPostDto> BoardSearchPage(BoardSearch search, Pageable pageable) {
        List<BoardPostDto> pageContents =
                factory.select(new QBoardPostDto(
                        post.title, member.nickname, post.createDate, post.id)
                ).from(post)
                .join(post.member, member)
                .where(titleEq(search.getTitle()),
                        nicknameEq(search.getNickname()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = factory.select(post.count())
                .from(post)
                .leftJoin(post.member, member)
                .where(nicknameEq(search.getNickname()), titleEq(search.getTitle()));

        return PageableExecutionUtils.getPage(pageContents,pageable, () -> countQuery.fetchOne());


    }

    private BooleanExpression nicknameEq(String nickname) {
        return hasText(nickname) ? member.nickname.eq(nickname) : null;
    }

    private BooleanExpression titleEq(String title) {
        return hasText(title) ? post.title.eq(title) : null;
    }
}
