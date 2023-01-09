package yms.shopping.portfolio.controller.dto;

import lombok.Data;
import yms.shopping.portfolio.domain.board.Inquiry;

@Data
public class InquiryDto {
    private Long id;

    private String inquiry;

    public InquiryDto(Inquiry inquiry) {
        id = inquiry.getId();
        this.inquiry = inquiry.getInquiry();
    }
}
