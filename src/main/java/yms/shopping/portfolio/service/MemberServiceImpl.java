package yms.shopping.portfolio.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yms.shopping.portfolio.domain.Member;
import yms.shopping.portfolio.repository.MemberJpaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MemberServiceImpl {
    private final MemberJpaRepository memberJpaRepository;


    @Transactional
    public void signUp(Member member) {
        memberJpaRepository.save(member);
    }

    public Page<Member> pageList(Pageable pageable) {
        return memberJpaRepository.findAll(pageable);
    }

    public Member findMember(Long id) {
        return memberJpaRepository.findById(id).orElse(null);
    }

    public List<Member> findMembers() {
        List<Member> members = memberJpaRepository.findAll();
        return members;
    }
}
