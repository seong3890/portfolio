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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import yms.shopping.portfolio.controller.dto.MemberDto;
import yms.shopping.portfolio.controller.dto.PageName;
import yms.shopping.portfolio.controller.validator.MemberValidator;
import yms.shopping.portfolio.domain.Member;
import yms.shopping.portfolio.service.MemberServiceImpl;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberServiceImpl memberService;
    private final MemberValidator memberValidator;

    @InitBinder
    public void initValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(memberValidator);
    }

    @GetMapping("/signup")
    public String memberSignForm(@ModelAttribute("memberDto") MemberDto memberDto, Model model) {
        return "account/signForm";
    }

    @PostMapping("/signup")
    public String memberSignUp(@Validated MemberDto memberDto, BindingResult result) {
        if (result.hasErrors()) {
            return "account/signForm";
        }
        Member member = memberDto.createMember(memberDto);
        memberService.signUp(member);
        log.info("member={}", member.toString());
        return "redirect:/";
    }

    //@GetMapping("/pageing")
    public String pageing(Model model, @PageableDefault(sort = "id") Pageable pageable) {
        Page<Member> members = memberService.pageList(pageable);
        Page<PageName> page = members.map(member -> new PageName(member));


      /*  int nowPage = page.getPageable().getPageSize() + 1;
        int startPage = Math.max(nowPage-4, 1);
        int endPage = Math.min(nowPage + 9, page.getTotalPages());*/

        int nowPage = page.getPageable().getPageSize()+1;
        int startPage = page.getNumber();
        int endPage = page.getTotalPages();
        log.info("nowPage={}",page.getPageable().getPageSize());
        log.info("startPage={}",page.getNumber());
        log.info("endPage={}",page.getTotalPages());

        model.addAttribute("page", page);
//        model.addAttribute("nowPage", nowPage);
//        model.addAttribute("startPage", startPage);
//        model.addAttribute("endPage", endPage);
        model.addAttribute("maxPage", 10);
        return "pageEx";

    }
}
