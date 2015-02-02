package com.cloudzon.huddle.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import com.cloudzon.huddle.repository.RolePermissionRepository;
import com.cloudzon.huddle.repository.UserRepository;
import com.cloudzon.huddle.repository.UserRoleRepository;
import com.cloudzon.huddle.security.AccessDeniedHandlerImpl;
import com.cloudzon.huddle.security.AuthenticationTokenAndSessionProcessingFilter;
import com.cloudzon.huddle.security.CustomAccessDecisionManager;
import com.cloudzon.huddle.security.CustomAuthenticationProvider;
import com.cloudzon.huddle.security.CustomInvocationSecurityMetadataSource;
import com.cloudzon.huddle.security.CustomeUserDetailService;
import com.cloudzon.huddle.security.InMemoryTokenStore;
import com.cloudzon.huddle.security.UnauthorizedEntryPoint;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Resource
	private UserRepository userRepository;

	@Resource
	private UserRoleRepository userRoleRepository;

	@Resource
	private RolePermissionRepository rolePermissionRepository;

	@Autowired
	private InMemoryTokenStore tokenStore;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncode;

	@Bean
	public FilterSecurityInterceptor filterSecurityInterceptor()
			throws Exception {
		FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
		filterSecurityInterceptor
				.setAuthenticationManager(authenticationManager());
		filterSecurityInterceptor
				.setAccessDecisionManager(accessDecisionManagerBean());
		filterSecurityInterceptor
				.setSecurityMetadataSource(securityMetadataSource());
		return filterSecurityInterceptor;
	}

	@Bean
	public CustomAccessDecisionManager accessDecisionManagerBean() {
		return new CustomAccessDecisionManager("ANONYMOUS");
	}

	@Bean
	public CustomAuthenticationProvider authenticationProvider() {
		return new CustomAuthenticationProvider(userDetailsService(),
				bCryptPasswordEncode);
	}

	@Bean
	public CustomInvocationSecurityMetadataSource securityMetadataSource() {
		return new CustomInvocationSecurityMetadataSource(
				rolePermissionRepository);
	}

	@Bean
	public AuthenticationTokenAndSessionProcessingFilter authenticationTokenProcessingFilter() {
		return new AuthenticationTokenAndSessionProcessingFilter(tokenStore,
				"ANONYMOUS", "anonymousUser");
	}

	@Bean
	public UnauthorizedEntryPoint unauthorizedEntryPoint() {
		return new UnauthorizedEntryPoint();
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new AccessDeniedHandlerImpl();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
				.disable()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.addFilter(filterSecurityInterceptor())
				.addFilterBefore(authenticationTokenProcessingFilter(),
						FilterSecurityInterceptor.class).exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler())
				.authenticationEntryPoint(unauthorizedEntryPoint());
	}

	@Override
	protected UserDetailsService userDetailsService() {
		return new CustomeUserDetailService(userRepository, userRoleRepository);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}
}
