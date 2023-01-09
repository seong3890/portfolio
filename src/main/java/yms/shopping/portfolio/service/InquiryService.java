package yms.shopping.portfolio.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.stylesheets.LinkStyle;
import yms.shopping.portfolio.domain.board.Inquiry;
import yms.shopping.portfolio.repository.InquiryJpaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class InquiryService {
    private final InquiryJpaRepository inquiryJpaRepository;

    public List<Inquiry> findAll() {
        return inquiryJpaRepository.findAll();
    }

    public Inquiry findInquiry(Long id) {
        return inquiryJpaRepository.findById(id).orElse(null);
    }
}
