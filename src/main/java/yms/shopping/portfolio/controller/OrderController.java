package yms.shopping.portfolio.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import yms.shopping.portfolio.controller.dto.BoardSearch;
import yms.shopping.portfolio.controller.dto.OrderDto;
import yms.shopping.portfolio.controller.dto.OrderSearch;
import yms.shopping.portfolio.domain.Member;
import yms.shopping.portfolio.domain.item.Items;
import yms.shopping.portfolio.domain.order.Order;
import yms.shopping.portfolio.repository.OrderJpaRepository;
import yms.shopping.portfolio.service.ItemService;
import yms.shopping.portfolio.service.MemberServiceImpl;
import yms.shopping.portfolio.service.OrderService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class OrderController {
    private final OrderJpaRepository orderJpaRepository;
    private final OrderService orderService;
    private final MemberServiceImpl memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String orderGet(Model model) {
        List<Member> members = memberService.findMembers();
        List<Items> items = itemService.findItems();
        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String orderPost(Long memberId, Long itemId, int count) {
        Long orderId = orderService.order(memberId, itemId, count);

        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderPageList(@PageableDefault(size = 5, page = 0) Pageable pageable
            , @ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        Page<OrderDto> pageList = orderService.findPageList(orderSearch, pageable);
        model.addAttribute("order", pageList);
        for (OrderDto orderDto : pageList) {
//            System.out.println("orderDto = " + orderDto.getOrderInfoDtoList().get(0).getName());
        }
        log.info("orderDto={}",pageList.get());
        return "order/orderList";
    }
    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }



}
