package yms.shopping.portfolio.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yms.shopping.portfolio.domain.Member;
import yms.shopping.portfolio.domain.item.Items;
import yms.shopping.portfolio.repository.MemberJpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberLoginService {
    private final MemberJpaRepository memberJpaRepository;


    public Member Login(String username, String password) {
        log.info("service username={}",username);
        log.info("service password={}",password);
        Optional<Member> findMember = memberJpaRepository.findByUsernameAndPassword(username, password);
        if (findMember.isEmpty()) {
            return new Member();
        }
        return findMember.get();
    }


}
