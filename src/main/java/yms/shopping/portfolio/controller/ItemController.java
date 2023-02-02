package yms.shopping.portfolio.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import yms.shopping.portfolio.controller.dto.item.ItemBookPostDto;
import yms.shopping.portfolio.controller.dto.item.ItemMainDto;
import yms.shopping.portfolio.controller.dto.item.ItemPatchDto;
import yms.shopping.portfolio.domain.Member;
import yms.shopping.portfolio.repository.FileStore;
import yms.shopping.portfolio.repository.UploadImageJpaRepository;
import yms.shopping.portfolio.service.ItemService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import static yms.shopping.portfolio.SessionConst.LOGIN_MEMBER;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final FileStore fileStore;
    private final UploadImageJpaRepository uploadImageJpaRepository;


    @GetMapping("/item")
    public String items(@PageableDefault Pageable pageable, Model model) {
        Page<ItemMainDto> item = itemService.findItemJoinUpload(pageable);
        model.addAttribute("item", item);
        return "item/items";
    }

    @GetMapping("/item/insert")
    public String saveForm(Model model, @SessionAttribute(name = LOGIN_MEMBER) Member member) {
        model.addAttribute("item", new ItemBookPostDto());
        model.addAttribute("memberId", member.getId());
        return "item/insertItem";
    }

    @PostMapping("/item/insert")
    public String save(@Validated @ModelAttribute("item") ItemBookPostDto itemBookPostDto, BindingResult result
            , @RequestParam("memberId") Long id) throws IOException {
        if (result.hasErrors()) {
            return "item/insertItem";
        }
        itemService.create(id, itemBookPostDto);
        return "redirect:/item";
    }

    @GetMapping("/item/{itemId}")
    public String item(@PathVariable Long itemId,Model model) {
        ItemMainDto item = itemService.findItem(itemId);
        model.addAttribute("item", item);
        return "item/item";
    }

    @GetMapping("/item/{itemId}/edit")
    public String itemEditGet(@PathVariable Long itemId, Model model) {
        ItemPatchDto petchItem = itemService.findPetchItem(itemId);
        model.addAttribute("item", petchItem);
        return "item/itemEdit";
    }
    @PostMapping("/item/{itemId}/edit")
    public String itemEditPost(@Validated @ModelAttribute("item") ItemPatchDto itemPatchDto, BindingResult result
            , RedirectAttributes attributes,@PathVariable Long itemId) {
        log.info("itemEditPost");
        if (result.hasErrors()) {
            log.info("itemEditPostError");
            return "item/itemEdit";
        }
        log.info("itemEditPostStart");
        attributes.addAttribute("id", itemId);
        itemService.PetchItem(itemPatchDto,itemId);
        return "redirect:/item/" + attributes.getAttribute("id");
    }

    @ResponseBody
    @GetMapping("/images/{storeFileName}")
    public Resource resource(@PathVariable("storeFileName") String storeFileName) throws MalformedURLException {
        log.info("storeFileName={}",storeFileName);
        return new UrlResource("file:" + fileStore.getFullPath(storeFileName));
    }
}
