/*
package yms.shopping.portfolio.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig implements AccessDeniedHandler {
    */
/**
     * OAuth 2 관련 코드
     *//*

//    private final CustomOAuth2MemberService customOAuth2MemberService;


 //   @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
     */
/*   http
                //   .csrf().disable().headers().frameOptions().disable().and().
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/js/**", "/images/**").permitAll()
                .antMatchers("/item/insert", "/board/insertBoard", "/board/{boardId}/delete", "/board/{boardId}/edit")
                .hasRole(Role.USER.name()).anyRequest().authenticated()
                .and().logout().logoutSuccessUrl("/")
                .and().oauth2Login().loginPage("/login")
                .and().oauth2Login().userInfoEndpoint().userService(customOAuth2MemberService);*//*


        http.authorizeRequests()
                .antMatchers("/", "/login", "/logout", "/board", "/{boardId}", "/item", "/signup").permitAll()
                .antMatchers("/item/insert\", \"/board/insertBoard\", \"/board/{boardId}/delete\", \"/board/{boardId}/edit").authenticated()
                .antMatchers("/login").anonymous()
                .mvcMatchers(HttpMethod.GET, "/profile/*").permitAll();
                */
/*.anyRequest().authenticated();*//*

               */
/* .and()
                .formLogin().loginPage("/login")
                .permitAll();*//*

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

    }
}
*/
