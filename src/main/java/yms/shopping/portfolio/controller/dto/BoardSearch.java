package yms.shopping.portfolio.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardSearch {

    private String nickname;

    private String title;

    public BoardSearch(String nickname, String title) {
        this.nickname = nickname;
        this.title = title;
    }
}
