package com.jgp.OnlineMarket.OnlineMarket.security;

import com.jgp.OnlineMarket.OnlineMarket.models.dao.ISellerEntityDAO;
import com.jgp.OnlineMarket.OnlineMarket.models.entities.SellerEntity;
import com.jgp.OnlineMarket.OnlineMarket.services.sellersService;
import com.jgp.OnlineMarket.OnlineMarket.utils.CustomAuthenticationFailureHandler;
import com.jgp.OnlineMarket.OnlineMarket.utils.Utils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private sellersService sellersService;
    private final CustomAuthenticationFailureHandler failureHandler;

    public SecurityConfig(sellersService sellersService, CustomAuthenticationFailureHandler failureHandler) {
        this.sellersService = sellersService;
        this.failureHandler = failureHandler;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return cif -> {
            SellerEntity seller = sellersService.findSellerByCif(cif);

            if (seller == null) {
                throw new UsernameNotFoundException("Seller CIF not found");
            }

            return User.withUsername(seller.getCif())
                    .password(seller.getPassword())
                    .roles("SELLER") // not really necessary
                    .build();
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return Utils.MD5Converter(rawPassword.toString()).toUpperCase();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encode(rawPassword).equals(encodedPassword);
            }
        };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/login","/css/**", "/img/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .failureUrl("/login?error")
                    .failureHandler(failureHandler)
                    .defaultSuccessUrl("/view/index", true)
                    .permitAll()
                .and()
                .logout()
                    .logoutSuccessUrl("/login")
                    .permitAll()
                .and()
                .httpBasic()
                .and()
                .sessionManagement()
                    .maximumSessions(-1)
                    .maxSessionsPreventsLogin(false);
    }
}
