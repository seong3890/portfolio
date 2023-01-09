package yms.shopping.portfolio.controller.dto;

import lombok.Data;
import yms.shopping.portfolio.domain.Member;

@Data
public class PageName {
    private String username;

    private String nickname;

    public PageName(Member member) {

        this.username = member.getUsername();
        this.nickname = member.getNickname();
    }
}
