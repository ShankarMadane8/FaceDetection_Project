package com.crud.config;


import java.util.Collection;

import org.hibernate.usertype.UserType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class MyConfig {

	@Bean
	public UserDetailsService getUserDetailsService() {
		return new UserDetailServiceImp();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(getUserDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

//	@Bean
//	public AuthenticationManager authenticationManager(
//	        AuthenticationConfiguration authConfig) throws Exception {
//	    return authConfig.getAuthenticationManager();
//	}

//	@Bean 
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http.authenticationProvider(authenticationProvider());
//		
//		http.authorizeRequests()
//		.antMatchers("/admin/**").hasAuthority("admin")
//		.antMatchers("/normal/**").hasAnyAuthority("normal","admin","special")
//		.antMatchers("/special/**").hasAnyAuthority("special","admin")
//		.antMatchers("/**").permitAll().and()
//		.formLogin().loginPage("/login").usernameParameter("email")
//		.defaultSuccessUrl("/checkDashboard", true).and()
//		.csrf().disable();
//		return http.build();
//	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authenticationProvider(authenticationProvider());

		http.authorizeRequests().antMatchers("/newface").authenticated().antMatchers("/admin/**").hasAuthority("admin").antMatchers("/normal/**")
				.hasAnyAuthority("normal", "admin").antMatchers("/special/**").hasAnyAuthority("special", "admin")
				.antMatchers("/**").permitAll().and().formLogin().loginPage("/login").usernameParameter("email")
				.successHandler(mySuccessHandler()).and().csrf().disable();
		return http.build();
	}

	private AuthenticationSuccessHandler mySuccessHandler() {
		
		
		return (request, response, authentication) ->{ 
			String username = authentication.getName();
	        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
	        System.out.println("=======================================");
	        System.out.println("username:" +username);
	        System.out.println("autherities:"+authorities.toString());
	        String str="";
	        for(GrantedAuthority userType:authorities) {
	        	str=""+userType;
	        	System.out.println(new SimpleGrantedAuthority(""+userType)+" " +userType);
	        	break;
	        }     	
	        	
	        System.out.println("request:"+request);
	        System.out.println("response:"+response);
	        System.out.println("authentication:"+authentication);
	        System.out.println("=======================================");
			response.sendRedirect("/"+str+"/dashboard");
		};
		
		
	}
	

}
