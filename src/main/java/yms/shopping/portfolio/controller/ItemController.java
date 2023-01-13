package yms.shopping.portfolio.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import yms.shopping.portfolio.SessionConst;
import yms.shopping.portfolio.controller.dto.item.ItemBookDto;
import yms.shopping.portfolio.controller.dto.item.ItemMainDto;
import yms.shopping.portfolio.domain.Member;
import yms.shopping.portfolio.repository.FileStore;
import yms.shopping.portfolio.repository.UploadImageJpaRepository;
import yms.shopping.portfolio.service.ItemService;
import yms.shopping.portfolio.service.MemberServiceImpl;

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
    public String items(Model model) {
        List<ItemMainDto> item = itemService.findItemJoinUpload();
        model.addAttribute("item", item);
        return "item/items";
    }

    @GetMapping("/item/insert")
    public String saveForm(Model model, @SessionAttribute(name = LOGIN_MEMBER) Member member) {
        model.addAttribute("item", new ItemBookDto());
        model.addAttribute("memberId", member.getId());
        return "item/insertItem";
    }

    @PostMapping("/item/insert")
    public String save(@Validated @ModelAttribute("item") ItemBookDto itemBookDto, BindingResult result
            , @RequestParam("memberId") Long id) throws IOException {
        if (result.hasErrors()) {
            return "item/insertItem";
        }
        itemService.create(id, itemBookDto);
        return "redirect:/item";
    }

    @ResponseBody
    @GetMapping("/images/{storeFileName}")
    public Resource resource(@PathVariable("storeFileName") String storeFileName) throws MalformedURLException {
        log.info("storeFileName={}",storeFileName);
        return new UrlResource("file:" + fileStore.getFullPath(storeFileName));
    }
}
