package com.ufop.HelpSind;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;


@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfiguration{

	@Autowired
	DataSource dataSource;
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
        		.requestMatchers("/trustee/**").hasAuthority("TRUSTEE")
        		.requestMatchers("/tenant/**").hasAuthority("TENANT")
        		.requestMatchers("/admin/**").hasAuthority("ADMIN")
        		.requestMatchers("/auth/**", "/account/cadastro/**").authenticated()
    		.and().formLogin()
        			.loginPage("/entrar")
        			.failureUrl("/entrar?erro")
        			.successForwardUrl("/trustee")
        			.defaultSuccessUrl("/auth")
        			.usernameParameter("cpf").passwordParameter("password")
    		.and().logout()
        			.logoutSuccessUrl("/entrar?sair")
        			.logoutUrl("/sair")
        			.invalidateHttpSession(true)
        			.clearAuthentication(true)
    		.and().rememberMe()
        			.tokenRepository(persistentTokenRepository())
        			.tokenValiditySeconds(120960)
    		.and().csrf();
        return http.build();
	}
	
	@Bean
	public AuthenticationManager authManager(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
			.usersByUsernameQuery("select cpf,password,active from users where cpf=?")
			.authoritiesByUsernameQuery(
					"select cpf,auth from users join auth on id = id_user where cpf=?");
		 auth
         .inMemoryAuthentication()
             .withUser("user").password("123456").roles("USER");

		return auth.build();
	}
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}
	
	@Bean
	public SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() {
		SavedRequestAwareAuthenticationSuccessHandler auth = new SavedRequestAwareAuthenticationSuccessHandler();
		auth.setTargetUrlParameter("targetUrl");
		return auth;
	}
	
	
}
