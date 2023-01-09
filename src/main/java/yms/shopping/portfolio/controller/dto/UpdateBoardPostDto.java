package yms.shopping.portfolio.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBoardPostDto {
    private Long id;

    private String title;

    private String write;

    private String inquiry;
    private Long inquiryId;

    private String nickname;

    public UpdateBoardPostDto(Long id, String title, String write, String inquiry, String nickname) {
        this.id = id;
        this.title = title;
        this.write = write;
        this.inquiry = inquiry;
        this.nickname = nickname;
    }
}
