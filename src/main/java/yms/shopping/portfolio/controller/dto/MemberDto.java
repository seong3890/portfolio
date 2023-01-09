package yms.shopping.portfolio.controller.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import yms.shopping.portfolio.domain.Address;
import yms.shopping.portfolio.domain.Member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
public class MemberDto {

    @NotBlank
    @Length(min = 3, max = 20)
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{3,20}$")
    private String username;

    @NotBlank
    @Length(min = 3, max = 20)
    private String nickname;

    @NotBlank
    @Length(min = 8, max = 50)
    private String password;

    private String city;
    private String address;

    public Member createMember(MemberDto memberDto) {
        Member member = new Member(memberDto.getUsername(), memberDto.getNickname(), memberDto.getPassword()
                , new Address(memberDto.getCity(), memberDto.getAddress()), LocalDateTime.now());
        return member;
    }
}
