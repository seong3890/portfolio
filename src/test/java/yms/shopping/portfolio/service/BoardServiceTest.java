package yms.shopping.portfolio.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import yms.shopping.portfolio.domain.Member;
import yms.shopping.portfolio.domain.board.Inquiry;
import yms.shopping.portfolio.domain.board.Post;
import yms.shopping.portfolio.repository.InquiryJpaRepository;
import yms.shopping.portfolio.repository.MemberJpaRepository;
import yms.shopping.portfolio.repository.PostJpaRepository;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    PostJpaRepository postJpaRepository;
    @Autowired
    MemberJpaRepository memberJpaRepository;
    @Autowired
    InquiryJpaRepository inquiryJpaRepository;

    @Test
    void getPost() {
        Post post = postJpaRepository.findById(35L).orElse(null);

        Member member = memberJpaRepository.findById(post.getMember().getId()).orElse(null);
        Inquiry inquiry = inquiryJpaRepository.findById(post.getInquiry().getId()).orElse(null);

        assertThat(member.getNickname()).isEqualTo("kane123");

    }
}