package yms.shopping.portfolio.controller.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import yms.shopping.portfolio.domain.Member;
import yms.shopping.portfolio.domain.board.Inquiry;
import yms.shopping.portfolio.domain.board.Post;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Slf4j
public class BoardPostDto {

    @NotBlank
    private String title;

    private String write;

    private String nickname;

    private String inquiry;

    private String dateTime;

    private LocalDateTime createDate;

    private Long id;

    public BoardPostDto() {
    }
    @QueryProjection
    public BoardPostDto(String title,  String nickname, LocalDateTime createDate, Long id) {
        this.title = title;
        this.nickname = nickname;
        this.createDate = createDate;
        this.dateTime = createDate.format(DateTimeFormatter.ofPattern("yy-MM-dd HH:SS"));
        this.id = id;
    }

    public BoardPostDto(Post post) {

        title = post.getTitle();
        write = post.getWrite();
        id = post.getId();
        nickname = post.getMember().getNickname();
//        inquiry=post.getInquiry().getInquiry();
    }

    static public BoardPostDto createBoardPostDto(Post post) {
        BoardPostDto postDto = new BoardPostDto();
        postDto.setTitle(post.getTitle());
        postDto.setWrite(post.getWrite());
        postDto.setId(post.getId());
        postDto.setNickname(post.getMember().getNickname());
        postDto.setDateTime(post.getCreateDate().format(DateTimeFormatter.ofPattern("yy-mm-dd hh:ss")));
        return postDto;
    }

    static public BoardPostDto getBoardPostDto(Post post, Member member, Inquiry inquiry) {
        BoardPostDto postDto = new BoardPostDto();
        postDto.setTitle(post.getTitle());
        postDto.setWrite(post.getWrite());
        postDto.setId(post.getId());
        postDto.setNickname(member.getNickname());
        postDto.setInquiry(inquiry.getInquiry());
        log.info("getBoardPost={}",postDto);

        return postDto;
    }
}
