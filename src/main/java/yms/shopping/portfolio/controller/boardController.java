package yms.shopping.portfolio.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import yms.shopping.portfolio.controller.dto.BoardPostDto;
import yms.shopping.portfolio.controller.dto.BoardSearch;
import yms.shopping.portfolio.controller.dto.UpdateBoardPostDto;
import yms.shopping.portfolio.domain.Member;
import yms.shopping.portfolio.domain.board.Inquiry;
import yms.shopping.portfolio.repository.PostJpaRepository;
import yms.shopping.portfolio.service.BoardService;
import yms.shopping.portfolio.service.InquiryService;
import yms.shopping.portfolio.service.MemberServiceImpl;

import java.util.List;

import static yms.shopping.portfolio.SessionConst.LOGIN_MEMBER;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
@Slf4j
public class boardController {
    private final BoardService boardService;
    private final MemberServiceImpl memberService;
    private final InquiryService inquiryService;
    private final PostJpaRepository postJpaRepository;


    /**
     * 화면 페이지 O 검색 O
     */

    @GetMapping
    public String boardListSearchPage(@PageableDefault(size = 5, page = 0) Pageable pageable
            ,@ModelAttribute("search") BoardSearch search, Model model) {
        Page<BoardPostDto> postDto2 = boardService.findItemListSearchPage(search,pageable);
        log.info("number={}", postDto2.getNumber());
        log.info("getTotalPages={}", postDto2.getTotalPages());
        log.info("getTotalElements={}", postDto2.getTotalElements());
        double floor = Math.floor((postDto2.getNumber() / 10) * 10);

        log.info("start={}", floor);

        model.addAttribute("post", postDto2);
        return "board/list";
    }


    /**
     * 단순 화면 페이지 X 검색 X
     */
  //  @GetMapping
    public String boardList(Model model) {
        List<BoardPostDto> postDto2 = boardService.findItemList();

//        List<BoardPostDto> postDto2 = postList.stream().map(post -> new BoardPostDto(post)).collect(toList());
//        List<BoardPostDto> postDto2 = postList.stream().map(post -> BoardPostDto.createBoardPostDto(post)).collect(toList());
//        log.info("nickname={}",postList.get(0).getMember().getNickname());

        model.addAttribute("post", postDto2);
        return "board/list";
    }

    /**
     * 화면 페이지 O 검색 X
     */
   // @GetMapping
    public String boardListPage(@PageableDefault(size = 5, page = 0) Pageable pageable, Model model) {
        Page<BoardPostDto> postDto2 = boardService.findItemListPage(pageable);
        log.info("number={}", postDto2.getNumber());
        log.info("getTotalPages={}", postDto2.getTotalPages());
        log.info("getTotalElements={}", postDto2.getTotalElements());
        double floor = Math.floor((postDto2.getNumber() / 10) * 10);

        log.info("start={}", floor);

//        List<BoardPostDto> postDto2 = postList.stream().map(post -> new BoardPostDto(post)).collect(toList());
//        List<BoardPostDto> postDto2 = postList.stream().map(post -> BoardPostDto.createBoardPostDto(post)).collect(toList());
//        log.info("nickname={}",postList.get(0).getMember().getNickname());

        model.addAttribute("post", postDto2);
        return "board/list";
    }




    /**
     * 개선 필요
     * 굳이 dto 로 변환할 필요가 있을까? -> 바꾸는 게 좋다.
     * repository 에서 바로 dto 로 변환할 수도 있다.
     */
    @GetMapping("/{boardId}")
    public String boards(@PathVariable Long boardId, Model model) {

        BoardPostDto postDto = boardService.findGetBoard(boardId);
        model.addAttribute("postDto", postDto);
        return "board/board";
    }

    @GetMapping("/{boardId}/edit")
    public String updateForm(@PathVariable("boardId") Long id,Model model) {
        UpdateBoardPostDto postsForm = boardService.findPostsForm(id);
        List<Inquiry> inquiryList = inquiryService.findAll();
        model.addAttribute("post", postsForm);
        model.addAttribute("inquiry", inquiryList);
        return "board/edit";
    }

    @PostMapping("/{boardId}/edit")
    public String updateBoard(@PathVariable("boardId")Long id,UpdateBoardPostDto postDto,@RequestParam("inquiry")Long inquiry_id) {
        Long PostsId = boardService.update(id, postDto, inquiry_id);
        return "redirect:/board/" + PostsId;
    }

    @GetMapping("/insertBoard")
    public String insertBoardForm(Model model,
                                  @SessionAttribute(name = LOGIN_MEMBER) Member member) {
//        List<InquiryDto> inquiryList = inquiryService.findAll().stream().map(q -> new InquiryDto(q)).collect(toList());
        List<Inquiry> inquiryList = inquiryService.findAll();

        model.addAttribute("inquiry", inquiryList);
        model.addAttribute("postDto", new BoardPostDto());
        model.addAttribute("nickname", member.getNickname());
        model.addAttribute("memberId", member.getId());
        return "board/insertBoard";

    }

    @PostMapping("/insertBoard")
    public String insertBoard(@Validated BoardPostDto postDto, BindingResult result,
                              @RequestParam("inquiryId") Long inquiry_id,
                              @RequestParam("memberId") Long memberId, Model model) {

        if (inquiry_id == null) {
            result.rejectValue("id","range","문의유형을 선택하세요");
        }
        if (result.hasErrors()) {
            return "board/insertBoard";
        }
        boardService.create(postDto, inquiry_id, memberId);
//        boardService.board();
        return "redirect:/board";
    }

    @DeleteMapping("/{boardId}/delete")
    @ResponseBody
    public Long deleteBoard(@PathVariable("boardId") Long id) {
        boardService.delete(id);
        return id;
    }


    @GetMapping("/hi")
    @ResponseBody
    public List<BoardPostDto> hihi() {
        return boardService.findItemList();
    }
    @GetMapping("/hi2")
    @ResponseBody
    public  List<Inquiry> hihi2() {
        return inquiryService.findAll();
    }


      /* @ResponseBody
    @GetMapping("board/aa")
    public List<BoardPostDto> boardList22(Model model) {
//        List<Post> postList = boardService.findItemList();
        List<BoardPostDto> postDto2 = boardService.findAll();

//        List<BoardPostDto> postDto2 = postList.stream().map(post -> new BoardPostDto(post)).collect(toList());
//        List<BoardPostDto> postDto2 = postList.stream().map(post -> BoardPostDto.createBoardPostDto(post)).collect(toList());
//        log.info("nickname={}",postList.get(0).getMember().getNickname());

        model.addAttribute("post", postDto2);
        return postDto2;
    }*/

}
