package yms.shopping.portfolio.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString(of = {"nickname", "address", "createSignup"})
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String username;

    private String nickname;


    private String password;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private InfoConsent Consent;

    /**
     *oauth2.0 추가 2023-01-19
     * 하지만 여기 member 로 자체 로그인을 해야하는데 구글 로그인이랑 겹쳐서 일단 보류
     * 시큐리티만 적용하려 함
     */

   /* @Column(nullable = false)
    private String email;

    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;*/


    public Member(String username, String nickname) {

        this.username = username;
        this.nickname = nickname;
    }

    public Member(String username, String nickname, String password, Address address) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.address = address;

    }

    /**
     * OAuth2.0 관련 메소드
     */
    /*public Member update(String username, String picture) {
        this.username = username;
        this.picture = picture;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }*/
}
