package yms.shopping.portfolio.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import yms.shopping.portfolio.SessionConst;
import yms.shopping.portfolio.controller.validator.LoginValidator;
import yms.shopping.portfolio.controller.dto.LoginDto;
import yms.shopping.portfolio.domain.Member;
import yms.shopping.portfolio.service.MemberLoginService;
import yms.shopping.portfolio.service.MemberServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final MemberLoginService loginService;
    private final LoginValidator loginValidator;


    @InitBinder
    public void initValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(loginValidator);
    }

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginDto") LoginDto loginDto) {
        return "account/login";
    }

    @PostMapping("/login")
    public String login(@Validated LoginDto loginDto, BindingResult result, HttpServletRequest request
            , @RequestParam(defaultValue = "/") String requestURL ) {
        if (result.hasErrors()) {
            return "account/login";
        }
        log.info("loginController username={}", loginDto.getUsername());
        log.info("loginController password={}", loginDto.getPassword());
        log.info("requestRUI={}", requestURL);
        Member loginMember = loginService.Login(loginDto.getUsername(), loginDto.getPassword());

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        return "redirect:" + requestURL;

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
