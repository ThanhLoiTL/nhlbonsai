package com.nhlshop.config;

import com.nhlshop.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/test/**").hasAuthority("SHIPPER")
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/shipper/**").hasAuthority("SHIPPER")
                .anyRequest().authenticated()
                .and().exceptionHandling().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        // http.csrf().disable().authorizeHttpRequests().antMatchers("/login").permitAll().anyRequest()
        // .authenticated().and()
        // .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        // .authorizeRequests()
        // .antMatchers("/admin/account").access("hasRole('ADMIN')")
        // .antMatchers("/shipper/**").access("hasRole('SHIPPER')").and()
        // .addFilterBefore(jwtRequestFilter,
        // UsernamePasswordAuthenticationFilter.class);

        // .cors()
        // .and()
        // .csrf()
        // .disable()
        // .exceptionHandling()
        // .authenticationEntryPoint(unauthorizedHandler) // Ném ngoại lệ
        // .and()
        // .sessionManagement()
        // .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        // .and()
        // .authorizeRequests()
        // .antMatchers()
        // .permitAll()
        // .antMatchers("/admin/account") // Đường dẫn /api/login-admin sẽ được truy cập
        // bình thường mà ko cần
        // // check
        // .permitAll()
        // .anyRequest()
        // .authenticated(); // Mọi đường dẫn còn lại yêu cầu gửi Authentication String
        // trên header để check.
    }
}
