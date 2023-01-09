package yms.shopping.portfolio.controller.validator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import yms.shopping.portfolio.controller.dto.LoginDto;
import yms.shopping.portfolio.domain.Member;
import yms.shopping.portfolio.repository.MemberJpaRepository;
import yms.shopping.portfolio.service.MemberLoginService;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoginValidator implements Validator {
    private final MemberLoginService memberLoginService;
    @Override

    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(LoginDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LoginDto loginDto = (LoginDto) target;
        Member login = memberLoginService.Login(loginDto.getUsername(), loginDto.getPassword());
        if (login.getUsername()==null || login.getPassword()==null ) {
            errors.reject("loginFail", "아이디 혹은 비밀번호가 맞지 않습니다.");
        }
        log.info("errors={}",errors.getFieldError());
        log.info("username={}",login.getUsername());
        log.info("password={}",login.getPassword());



    }
}
