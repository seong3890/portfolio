package yms.shopping.portfolio.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import yms.shopping.portfolio.SessionConst;
import yms.shopping.portfolio.controller.dto.item.ItemBookDto;
import yms.shopping.portfolio.domain.Member;
import yms.shopping.portfolio.repository.UploadImageJpaRepository;
import yms.shopping.portfolio.service.ItemService;
import yms.shopping.portfolio.service.MemberServiceImpl;

import java.io.IOException;

import static yms.shopping.portfolio.SessionConst.LOGIN_MEMBER;

@Slf4j
@RequestMapping("/item")
@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final UploadImageJpaRepository uploadImageJpaRepository;


    @GetMapping
    public String items(ItemBookDto itemBookDto, Model model) {
        model.addAttribute("item", itemBookDto);
        return "item/items";
    }

    @GetMapping("/insert")
    public String saveForm(Model model, @SessionAttribute(name = LOGIN_MEMBER) Member member) {
        model.addAttribute("item",new ItemBookDto());
        model.addAttribute("memberId", member.getId());
        return "item/insertItem";
    }

    @PostMapping("/insert")
    public String save(@Validated @ModelAttribute("item") ItemBookDto itemBookDto, BindingResult result
    ,@RequestParam("memberId") Long id) throws IOException {
        if (result.hasErrors()) {
            return "item/insertItem";
        }
        itemService.create(id, itemBookDto);
        return "redirect:/item";
    }

}
