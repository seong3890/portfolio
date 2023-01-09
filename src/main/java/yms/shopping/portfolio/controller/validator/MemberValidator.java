package yms.shopping.portfolio.controller.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import yms.shopping.portfolio.controller.dto.MemberDto;
import yms.shopping.portfolio.repository.MemberJpaRepository;

@Component
@RequiredArgsConstructor
public class MemberValidator implements Validator {
    private final MemberJpaRepository memberJpaRepository;
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(MemberDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MemberDto memberDto = (MemberDto) target;
        if (memberJpaRepository.existsByUsername(memberDto.getUsername())) {
            errors.rejectValue("username","name","사용중인 아이디입니다.");
        }

    }
}
