package yms.shopping.portfolio.domain.board;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import yms.shopping.portfolio.controller.dto.BoardPostDto;
import yms.shopping.portfolio.domain.BaseTimeEntity;
import yms.shopping.portfolio.domain.Member;
import yms.shopping.portfolio.domain.item.Items;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Table(name = "posts")
public class Post extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    @Column(length = 30,nullable = false)
    private String title;

    private String write;




   /* private LocalDateTime createPostDate;
    private LocalDateTime updatePostDate;*/

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "inquiry_id")
    private Inquiry inquiry;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "items_id")
    private Items Items;

    public Post(String title, String write, Member member, Items items) {
        this.title = title;
        this.write = write;

        this.member = member;
        Items = items;
    }

    //생성 메서드
    public static Post createPost(String title, String write, Member member, Items items) {
        Post post = new Post(title, write,  member, items);

        return post;
    }


    //생성 메서드2
    public static Post createPost(BoardPostDto postDto, Member member, Inquiry inquiry) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setWrite(postDto.getWrite());

//        post.setCreatePostDate(LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("YY-MM-DD hh:mm"))));
        post.setInquiry(inquiry);
        post.setMember(member);
        return post;
    }

    public  void updatePost(String title, String write, Inquiry inquiry) {
        this.title = title;
        this.write = write;
        this.inquiry = inquiry;
    }
}
