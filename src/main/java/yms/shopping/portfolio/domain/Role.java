package yms.shopping.portfolio.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * OAuth 2 관련 코드
 */
@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;
}
