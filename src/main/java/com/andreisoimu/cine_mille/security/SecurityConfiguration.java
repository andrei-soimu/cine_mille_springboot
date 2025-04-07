package com.andreisoimu.cine_mille.security;

import com.andreisoimu.cine_mille.model.dao.User;
import com.andreisoimu.cine_mille.security.filter.JwtRequestFilter;
import com.andreisoimu.cine_mille.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

	@Autowired
	public SecurityConfiguration(UserService userService, JwtRequestFilter jwtRequestFilter) {
		this.userService = userService;
		this.jwtRequestFilter = jwtRequestFilter;
	}
	private final UserService userService;

	private final JwtRequestFilter jwtRequestFilter;

	@Value("#{'${cors.allowed-origins}'.split(',')}")
	private List<String> allowedOrigins;

	@Value("#{'${cors.allowed-methods}'.split(',')}")
	private List<String> allowedMethods;

	@Value("${cors.allowed-headers}")
	private String allowedHeaders;

	@Value("${cors.allow-credentials}")
	private boolean allowCredentials;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

		httpSecurity
			.authorizeHttpRequests((auth) -> {
				try {
					auth
						.requestMatchers("/api/v1/screening/past_screenings").authenticated()
						.anyRequest().permitAll();
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			})
			.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.cors((cors) -> cors
					.configurationSource(apiConfigurationSource())
			)
			.csrf(AbstractHttpConfigurer::disable)
			.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

		return httpSecurity.build();
	}

	CorsConfigurationSource apiConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(allowedOrigins);
		configuration.setAllowedMethods(allowedMethods);
		configuration.setAllowedHeaders(Arrays.asList(allowedHeaders.split(",")));
		configuration.setAllowCredentials(allowCredentials);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(getPasswordEncoder());
		return authenticationProvider;
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			@Override
			public User loadUserByUsername(String username) throws UsernameNotFoundException {
				return userService.loadUserByUsername(username);
			}
		};
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
