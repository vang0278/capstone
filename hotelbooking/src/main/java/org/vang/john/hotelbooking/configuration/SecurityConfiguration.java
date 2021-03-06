package org.vang.john.hotelbooking.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.vang.john.hotelbooking.service.UserService;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http //
				.authorizeRequests() //
				.antMatchers( //
						"/", "/show", //
						"/registration**", //
						"/init**", //
						"/js/**", //
						"/css/**", //
						"/img/**", //
						"/webjars/**")
				.permitAll() //
				.antMatchers("/admin","/admin/**").hasRole("ADMIN") //
				.antMatchers("/employee","/employee/**").hasRole("EMPLOYEE") //
				.antMatchers("/user","/user/**").hasRole("USER") //
				.anyRequest().authenticated() //

				.and() //
				.formLogin() //
				.loginPage("/login") //
				.permitAll() //

				.and() //
				.logout() //
				.invalidateHttpSession(true) //
				.clearAuthentication(true) //
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //
				.logoutSuccessUrl("/login?logout") //
				.permitAll();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(this.userService);
		auth.setPasswordEncoder(this.passwordEncoder());
		return auth;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(this.authenticationProvider());
	}

}
