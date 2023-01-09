package yms.shopping.portfolio.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yms.shopping.portfolio.controller.dto.BoardPostDto;
import yms.shopping.portfolio.controller.dto.BoardSearch;
import yms.shopping.portfolio.controller.dto.UpdateBoardPostDto;
import yms.shopping.portfolio.domain.Member;
import yms.shopping.portfolio.domain.board.Inquiry;
import yms.shopping.portfolio.domain.board.Post;
import yms.shopping.portfolio.repository.InquiryJpaRepository;
import yms.shopping.portfolio.repository.ItemJpaRepository;
import yms.shopping.portfolio.repository.MemberJpaRepository;
import yms.shopping.portfolio.repository.PostJpaRepository;
import yms.shopping.portfolio.repository.query.PostQueryRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class BoardService {
    private final ItemJpaRepository itemJpaRepository;
    private final PostJpaRepository postJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final InquiryJpaRepository inquiryJpaRepository;

    private final PostQueryRepository postQueryRepository;


    public List<BoardPostDto> findItemList() {
        List<Post> postList = postJpaRepository.findPostJoinMembers();
        List<BoardPostDto> postDto2 = postList.stream().map(post -> BoardPostDto.createBoardPostDto(post)).collect(toList());
        return postDto2;
    }

    public Page<BoardPostDto> findItemListPage(Pageable pageable) {
        Page<Post> postList = postJpaRepository.findPostJoinMembersPage(pageable);
//        Page<Post> postList = postJpaRepository.findAll(pageable);
        Page<BoardPostDto> postDto2 = postList.map(post -> BoardPostDto.createBoardPostDto(post));

        return postDto2;
    }

    public Page<BoardPostDto> findItemListSearchPage(BoardSearch search, Pageable pageable) {
        Page<BoardPostDto> postList = postQueryRepository.BoardSearchPage(search,pageable);
//        Page<Post> postList = postJpaRepository.findAll(pageable);
//        Page<BoardPostDto> postDto2 = postList.map(post -> BoardPostDto.createBoardPostDto(post));

        return postList;
    }

    public Post findById(long boardId) {
        return postJpaRepository.findById(boardId).orElse(null);

    }

    public List<BoardPostDto> findAll() {
        List<Post> postList = postJpaRepository.findPostJoinMembers();
        List<BoardPostDto> postDto2 = postList.stream().map(post -> BoardPostDto.createBoardPostDto(post)).collect(toList());
        return postDto2;
    }


    @Transactional
    public void create(BoardPostDto postDto, Long inquiry_id, Long memberId) {
        Member member = memberJpaRepository.findById(memberId).get();
        Inquiry inquiry = inquiryJpaRepository.findById(inquiry_id).get();
        Post post = Post.createPost(postDto, member, inquiry);
        log.info("post={}", post.getCreateDate());
        postJpaRepository.save(post);

    }


    public BoardPostDto findGetBoard(long boardId) {
        Post post = postJpaRepository.findById(boardId).orElse(null);

        Member member = memberJpaRepository.findById(post.getMember().getId()).orElse(null);
        Inquiry inquiry = inquiryJpaRepository.findById(post.getInquiry().getId()).orElse(null);
        return BoardPostDto.getBoardPostDto(post, member, inquiry);
    }

    public UpdateBoardPostDto findPostsForm(Long id) {
        UpdateBoardPostDto postDto = postQueryRepository.findPostAndMemberAndInquiryV2(id);
        return postDto;
    }

    @Transactional
    public Long update(Long id, UpdateBoardPostDto postDto, Long inquiry_id) {
        Inquiry inquiry = inquiryJpaRepository.findById(inquiry_id).get();
        Post post = postJpaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        post.updatePost(postDto.getTitle(), postDto.getWrite(), inquiry);
        return id;
    }

    @Transactional
    public void delete(Long id) {
        Post post = postJpaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        postJpaRepository.delete(post);


    }
}
