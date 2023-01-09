package yms.shopping.portfolio;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import yms.shopping.portfolio.domain.Member;

@Controller
@RequestMapping("/")
@Slf4j
public class HomeController {
    @GetMapping
    public String home(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                       Member member, Model model) {
        if (member == null) {
            return "home";
        }
        log.info("member={}",member.toString());
        model.addAttribute("member", member);
        return "LoginHome";
    }
}