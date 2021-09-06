package com.kh.happve.config;

import com.kh.happve.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity 
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final MemberService memberService;
	private final DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
		    .mvcMatchers("/","/login","/signup","/api/**","/index/**").permitAll()
		    .mvcMatchers(HttpMethod.GET,"/mypage/*").permitAll()
		    .mvcMatchers("/admin").hasRole("ADMIN")
		    .anyRequest().authenticated();
		
		http.formLogin()
	    .loginPage("/login")
	    .permitAll();
	
	    http.logout()
	    .logoutSuccessUrl("/");
	    
	    //로그인 기억
	    http.rememberMe()
	    .userDetailsService(memberService)
	    .tokenRepository(tokenRepository());
	}
	
	@Bean
	public PersistentTokenRepository tokenRepository() {
		JdbcTokenRepositoryImpl jdbcTokenRepository=new JdbcTokenRepositoryImpl();
		jdbcTokenRepository.setDataSource(dataSource);
		return jdbcTokenRepository;
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring()
		   .antMatchers("/favicon.ico","/static/css/**", "/resources/**", "/error","/getFiles/**")
		   .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}
}
