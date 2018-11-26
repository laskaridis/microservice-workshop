package it.laskaridis.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableAutoConfiguration
@EnableAuthorizationServer
@EnableResourceServer
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		return new JwtAccessTokenConverter();
	}

	@Bean
	public JwtTokenStore tokenStore(JwtAccessTokenConverter accessTokenConverter) {
		return new JwtTokenStore(accessTokenConverter);
	}

	@Bean
	public AuthorizationServerConfigurer authorizationServerConfigurer(
			JwtTokenStore jwtTokenStore,
			JwtAccessTokenConverter jwtAccessTokenConverter) {

		return new AuthorizationServerConfigurerAdapter() {
			@Override
			public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			    endpoints.tokenStore(jwtTokenStore).accessTokenConverter(jwtAccessTokenConverter);
			}

			@Override
			public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
			    security.checkTokenAccess("permitAll()");
			}
		};
	}

	@Bean
	public ResourceServerConfigurer resourceServerConfigurer() {
		return new ResourceServerConfigurerAdapter() {
			@Override
			public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
			    resources.resourceId("user");
			}

			@Override
			public void configure(HttpSecurity http) throws Exception {
			    http.authorizeRequests()
						.antMatchers(HttpMethod.POST, "/api/user").permitAll()
						.anyRequest().authenticated();
			}
		};
	}

}
