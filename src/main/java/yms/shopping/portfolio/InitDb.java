package yms.shopping.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import yms.shopping.portfolio.domain.Member;
import yms.shopping.portfolio.domain.board.Inquiry;
import yms.shopping.portfolio.domain.board.Post;
import yms.shopping.portfolio.domain.item.Book;
import yms.shopping.portfolio.repository.MemberJpaRepository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final MemberJpaRepository memberJpaRepository;

    private final HelloDb HelloDb;

  //  @PostConstruct
    public void init2() {
        HelloDb.init();
        //  HelloDb.itemInit();
    }


    @Component
    @RequiredArgsConstructor
    @Transactional
    static class HelloDb {
        private final EntityManager em;
        private final MemberJpaRepository memberJpaRepository;

        public void init() {
           /* Member member1 = em.find(Member.class, 9);
            Inquiry inquiry1 = em.find(Inquiry.class, 5);
            for (int i = 0; i < 100; i++) {
                Post post = Post.createPost("게시글" + i + "번입니다", i + "번째 글", member1, inquiry1);
                em.persist(post);

            }*/
            Member member = new Member();
            member.setUsername("asd");
            member.setNickname("kane123");
            member.setPassword("asdasd12#");
            em.persist(member);
          /*  Book book = createBook("jpa책", "김태진", 10000,100,"2331");
            em.persist(book);
            Book book2 = createBook("jpa책2", "김태순", 20000,100,"23123");
            em.persist(book2);
            Post post = Post.createPost("안녕하세요", "이거문의합니다얼마에요?", member, book);
            em.persist(post);*/
            Inquiry inquiry = new Inquiry("상품문의");
            Inquiry inquiry2 = new Inquiry("탈퇴문의");
            Inquiry inquiry3 = new Inquiry("배송문의");
            Inquiry inquiry4 = new Inquiry("기타문의");
            em.persist(inquiry);
            em.persist(inquiry2);
            em.persist(inquiry3);
            em.persist(inquiry4);

        }

        public void itemInit() {


        }

        private Book createBook(String name, String writer, int price,int quantity, String isbn) {
            Book book = new Book();
            book.setName(name);
            book.setWriter(writer);
            book.setPrice(price);
            book.setIsbn(isbn);
            book.setQuantity(quantity);
            return book;
        }

        private Book createBook2(String name, int price) {
            Book book = new Book();
            book.setName(name);

            book.setPrice(price);
            return book;
        }

    }
}
