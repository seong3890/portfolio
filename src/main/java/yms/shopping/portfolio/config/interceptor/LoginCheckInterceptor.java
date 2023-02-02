package yms.shopping.portfolio.config.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import yms.shopping.portfolio.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static yms.shopping.portfolio.SessionConst.LOGIN_MEMBER;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("로그인 인터셉터 실행");
        String requestURI = request.getRequestURI();
        request.setAttribute("requestURI",requestURI);
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(LOGIN_MEMBER) == null) {
            log.info("미인증 사용자");
            response.sendRedirect("/login?requestURL=" + requestURI);
            return false;
        }
        return true;
    }
}
