package com.devsuperior.movieflix.config;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

//import com.devsuperior.movieflix.controllers.UserResource;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	private static Logger logger = LoggerFactory.getLogger(ResourceServerConfig.class);
	
	@Autowired
	private Environment env;
	
	@Autowired
	private JwtTokenStore tokenStore;

	private static final String[] PUBLIC = { "/oauth/token", "/h2-console/**" };
	private static final String[] GETS_PUBLIC = {   "/xgenres22/**"};
	private static final String[] USER_AUTHENTICATED = {  "/users/**"  , "/movies/**" ,"/genres/**"  };
	private static final String[] ONLY_MEMBERS = {"/reviews/**"  };
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		logger.info("Passei no ResourceServerConfig.java ==> configure : ResourceServerSecurityConfigurer " );
		resources.tokenStore(tokenStore);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {

		// Banco H2 - liberando os frames de suas telas. 
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}
		
		
		logger.info("Passei no ResourceServerConfig.java ==> configure  " );
		http.authorizeRequests()
		.antMatchers(PUBLIC).permitAll()
		.antMatchers(HttpMethod.GET, GETS_PUBLIC).permitAll()
		.antMatchers(ONLY_MEMBERS).hasAnyRole("MEMBER", "ADMIN")
		.antMatchers(USER_AUTHENTICATED).hasAnyRole("MEMBER", "ADMIN","VISITOR")
		.anyRequest().authenticated();
	}	
}
