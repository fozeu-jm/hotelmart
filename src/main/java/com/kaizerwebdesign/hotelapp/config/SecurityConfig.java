package com.kaizerwebdesign.hotelapp.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import com.kaizerwebdesign.hotelapp.filters.JwtRequestFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig{
	
	
	@Configuration
	@Order(100)
	public static class ApiSecurityConfig extends WebSecurityConfigurerAdapter {
		@Autowired
		@Qualifier("securityDataSource")
		private DataSource securityDataSource;

		@Autowired
		UserDetailsService userDetailsService;

		@Autowired
		private JwtRequestFilter jwtRequestFilter;

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {

			// use jdbc authentication ... oh yeah!!!
			auth.userDetailsService(userDetailsService);

		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().requireCsrfProtectionMatcher(new AndRequestMatcher(CsrfFilter.DEFAULT_CSRF_MATCHER,
					new RegexRequestMatcher("^(?!/api/)", null)));
			
			http.authorizeRequests()
			.antMatchers("/api/customers/**").hasAnyRole("CUSTOMERS","ADMIN")
			.antMatchers("/api/rooms/**").hasAnyRole("ROOMS","ADMIN")
			.antMatchers("/api/reservations/**").hasAnyRole("RESERVATIONS","ADMIN")
			.antMatchers("/api/types/**").hasAnyRole("ROOM_TYPES","ADMIN")
			.antMatchers("/api/services/**").hasAnyRole("SERVICES","ADMIN")
			.antMatchers("/api/charges/**").hasAnyRole("CHARGES","ADMIN")
			.antMatchers("/api/payments/**").hasAnyRole("PAYMENTS","ADMIN")
			.antMatchers("/api/users/edit").hasRole("USER")
			.antMatchers("/api/users/connect").hasRole("USER")
			.antMatchers("/api/users/**").hasRole("ADMIN")
			.and().exceptionHandling().accessDeniedPage("/api/403");

			http.antMatcher("/api/**")
				.authorizeRequests()
				.antMatchers("/api/authentication").permitAll()
				.antMatchers("/api/**").authenticated()
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			
			http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		}
		
		@Override
		@Bean
		protected AuthenticationManager authenticationManager() throws Exception {
			return super.authenticationManager();
		}
		
		@Bean
		public PasswordEncoder passwordEncoder() {
		    return new BCryptPasswordEncoder();
		}
	}
	
	
	
	@Configuration
	@Order(10)
	public static class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		@Autowired
		@Qualifier("securityDataSource")
		private DataSource securityDataSource;

		@Autowired
		UserDetailsService userDetailsService;

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			
			auth.userDetailsService(userDetailsService);

		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
					.antMatchers("/web/customers/**").hasAnyRole("CUSTOMERS","ADMIN")
					.antMatchers("/web/rooms/**").hasAnyRole("ROOMS","ADMIN")
					.antMatchers("/web/reservations/**").hasAnyRole("RESERVATIONS","ADMIN")
					.antMatchers("/web/types/**").hasAnyRole("ROOM_TYPES","ADMIN")
					.antMatchers("/web/services/**").hasAnyRole("SERVICES","ADMIN")
					.antMatchers("/web/charges/**").hasAnyRole("CHARGES","ADMIN")
					.antMatchers("/web/payments/**").hasAnyRole("PAYMENTS","ADMIN")
					.antMatchers("/web/users/edit").hasRole("USER")
					.antMatchers("/web/users/**").hasRole("ADMIN")
					.and().exceptionHandling().accessDeniedPage("/error/403");
			
			http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
			http.antMatcher("/web/**").authorizeRequests()
			.antMatchers("/assets/**").permitAll()
			.antMatchers("/web/**").authenticated()
			.and().formLogin().loginPage("/web")
			.loginProcessingUrl("/web/authenticate").permitAll().and().logout().logoutUrl("/web/logout").permitAll();
		}
	}
}
