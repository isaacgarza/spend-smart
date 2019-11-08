package com.spendsmart.config;

import com.spendsmart.security.RestAuthenticationEntryPoint;
import com.spendsmart.security.SpendSmartUserDetailsService;
import com.spendsmart.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.spendsmart.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.spendsmart.security.oauth2.OAuth2AuthenticationSuccessHandler;
import com.spendsmart.security.oauth2.OAuth2UserService;
import com.spendsmart.security.token.TokenAuthenticationFilter;
import com.spendsmart.security.token.TokenProvider;
import com.spendsmart.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final SpendSmartUserDetailsService spendSmartUserDetailsService;
    private final OAuth2UserService oAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final TokenProvider tokenProvider;
    private final UserService userService;

    public WebSecurityConfiguration(SpendSmartUserDetailsService spendSmartUserDetailsService,
                                    OAuth2UserService oAuth2UserService,
                                    OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler,
                                    OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler,
                                    TokenProvider tokenProvider,
                                    UserService userService) {
        this.spendSmartUserDetailsService = spendSmartUserDetailsService;
        this.oAuth2AuthenticationFailureHandler = oAuth2AuthenticationFailureHandler;
        this.oAuth2UserService = oAuth2UserService;
        this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider, userService);
    }

    /*
      By default, Spring OAuth2 uses HttpSessionOAuth2AuthorizationRequestRepository to save
      the authorization request. But, since our service is stateless, we can't save it in
      the session. We'll save the request in a Base64 encoded cookie instead.
    */
    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(spendSmartUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .csrf().disable()
                    .formLogin().disable()
                    .httpBasic().disable()
                    .exceptionHandling().authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .and()
                    .authorizeRequests()
                        .antMatchers("/", "/error", "/login").permitAll()
                        .antMatchers("/oauth2/**", "/user").permitAll()
                        .anyRequest().authenticated()
                .and()
                    .oauth2Login().authorizationEndpoint()
                        .baseUri("/oauth2/authorize")
                        .authorizationRequestRepository(cookieAuthorizationRequestRepository())
                .and().redirectionEndpoint().baseUri("/oauth2/callback/*")
                .and().userInfoEndpoint().userService(oAuth2UserService)
                .and()
                    .successHandler(oAuth2AuthenticationSuccessHandler)
                    .failureHandler(oAuth2AuthenticationFailureHandler);

        // Add custom Token based authentication filter
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
