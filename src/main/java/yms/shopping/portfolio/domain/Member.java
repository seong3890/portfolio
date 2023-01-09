package yms.shopping.portfolio.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString(of = {"nickname","address","createSignup"})
public class Member {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String username;

    private String nickname;


    private String password;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private InfoConsent Consent;

    private LocalDateTime createSignup;
    private LocalDateTime updateMember;



    public Member(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
    }

    public Member(String username, String nickname, String password, Address address, LocalDateTime createSignup) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.address = address;
        this.createSignup = createSignup;
    }
}
