//package com.example.demo.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.DefaultSecurityFilterChain;
//import org.springframework.web.filter.CorsFilter;
//
//import com.example.demo.security.JwtAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity // 웹 보안을 활성화 하는데 사용한다 .
//// 스프링 시큐리티설정을 활성화 하는 역할 .
//public class WebSecurityConfig {
//
//	@Autowired
//	private JwtAuthenticationFilter jwtAtuhenticationFilter;
//
//	@Autowired
//	private WebMvcConfig webMvcConfig;
//
//	@Bean // Bean을 직접만드는 어노테이션 / 어노테이션을 사용해 직접객체를 만든다 .
//	protected DefaultSecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//		// HttpSecurity 설정
//		// 웹 보안을 설정하는데 사용되는 클래스 .
//		// HttpSecurity를 통해서 인증 권한부여 , csrf ,세션관리 등등 다 처리할 수 있다 .
//		http
//				// WebMvcConfig 에서 cors 설정을 했으므로 시큐리티에서는 비활성화한다 .
//				.cors(corsConfigurer -> corsConfigurer.disable())
//				// csrf : 사용자가 인증된 상태에서 공격자가 악의적으로 요청을 보내 사용자의 권한을 악용하는 공격기법 .
//				// API 서버가 클라이언트에서 Rest요청을 받을 때 CSRF보호가 불필요할 수 있다 .
//				.csrf(csrfConfigurer -> csrfConfigurer.disable())
//				// JWT를 사용하므로 Basic인증은 비활성화
//				.httpBasic(httpBasicConfigurer -> httpBasicConfigurer.disable())
//				// 세션을 사용하지않는다.
//				// sessionCreationPolicy() : 세션정책을 설정하는 메서드
//				// SessionCreationPolicy.STATELESS : 세션을 생성하지않고 stateless 방식으로 동작하도록 설정 .
//				.sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer
//						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//				// 요청에대한 인증/ 인가 설정
//				.authorizeHttpRequests(authorizeRequestsConfigurer ->
//				// 특정 경로는 인증없이 허용
//				authorizeRequestsConfigurer.requestMatchers("/", "/auth/**").permitAll()
//						// 그 외의 요청은 인증필요
//						.anyRequest().authenticated());
//		// filter등록
//		http.addFilterAfter(jwtAtuhenticationFilter, CorsFilter.class);
//
//		return http.build();
//	}
//
//}

package com.example.demo.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.demo.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
   
   @Autowired
   private JwtAuthenticationFilter jwtAuthenticationFilter;
   
   @Bean
   protected DefaultSecurityFilterChain securityFilterChain(
         HttpSecurity http) throws Exception {

      http
         .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
         .csrf(csrfConfigurer -> csrfConfigurer.disable())
         .httpBasic(httpBasicConfigurer -> httpBasicConfigurer.disable())
         .sessionManagement(sessionManagementConfigurer ->
               sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
           )
         
         .authorizeHttpRequests(authorizeRequestsConfigurer -> 
            authorizeRequestsConfigurer
            .requestMatchers("/", "/auth/**").permitAll()
            .anyRequest().authenticated()
         );

      http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

      return http.build();
   }

   @Bean
   public CorsConfigurationSource corsConfigurationSource() {
      CorsConfiguration configuration = new CorsConfiguration();
      //React 애플리케이션이 실행되는 출처에서 오는 요청을 허용
      configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000",
    		  "http://react-developer-env.eba-bfip8yjb.ap-northeast-2.elasticbeanstalk.com/",
    		  "http://app.foo1.shop/",
    		  "https://app.foo1.shop/")); // 프론트엔드 주소
      //HTTP 메서드 허용 get,post,put,delete,options
      configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
      //모든 헤더를 허용
      configuration.setAllowedHeaders(Arrays.asList("*"));
      // 쿠키나 인증 정보를 포함한 요청을 허용
      configuration.setAllowCredentials(true);
      
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", configuration);
      return source;
   }
}


