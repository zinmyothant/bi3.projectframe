package com.ace.web.pf.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.ace.web.pf.enums.AuthorityName;
import com.ace.web.pf.service.impl.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	/*
	 * To retrieve user detail information from the database for user validation of
	 * security,
	 */
	@Autowired
	private UserService jwtUserDetailsService;

	@Autowired
	AuthSuccessHandler customSuccessHandler;

	@Autowired
	CustomAccessDeniedHandler customAccessDeniedHandler;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	//	http;
		http.addFilterAfter(
		          new AuthenticationFilter(), BasicAuthenticationFilter.class).authorizeRequests()
				// Permit All
				
				.antMatchers("/").permitAll()		// Permit All
				//.antMatchers("/login").permitAll()
				// General Setting Save
//				.antMatchers("/generalSetting/{\\\\d+}/save").hasAnyAuthority(AuthorityName.ADMIN.toString())
//				// General Setting Delete
//				.antMatchers("/generalSetting/{\\\\d+}/delete").hasAnyAuthority(AuthorityName.ADMIN.toString())
//				// General Setting
//				.antMatchers("/generalSetting/**")
//				.hasAnyAuthority(AuthorityName.ADMIN.toString())
				.antMatchers("/generalSetting/{\\\\d+}/save").hasAnyAuthority(AuthorityName.ADMIN.toString())
				// General Setting Delete
				.antMatchers("/generalSetting/{\\\\d+}/delete").hasAnyAuthority(AuthorityName.ADMIN.toString())
				// General Setting
				.antMatchers("/generalSetting/student/")
				.hasAnyAuthority(
						AuthorityName.ADMIN.toString(), AuthorityName.COUNTER1.toString(),
						AuthorityName.COUNTER2.toString(), AuthorityName.CIS.toString()
						)
				// General Setting counter1
				.antMatchers("/generalSetting/student/counter1")
				.hasAnyAuthority(AuthorityName.ADMIN.toString(), AuthorityName.COUNTER1.toString(),
						AuthorityName.CIS.toString())
				.antMatchers("/generalSetting/student/counter2")
				.hasAnyAuthority(AuthorityName.ADMIN.toString(), AuthorityName.COUNTER2.toString(),
						AuthorityName.CIS.toString())
				.antMatchers("generalSetting/student/cis")
				.hasAnyAuthority(AuthorityName.ADMIN.toString(), AuthorityName.CIS.toString())

				.anyRequest().authenticated().and().csrf().disable().formLogin().loginPage("/").successHandler(customSuccessHandler)
				.defaultSuccessUrl("/generalSetting/student/", true)//.failureUrl("/login?error=true").usernameParameter("userName")
				.passwordParameter("password").and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/").invalidateHttpSession(true).and().exceptionHandling()
				.accessDeniedHandler(customAccessDeniedHandler);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/img/**", "/select2/**");
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
