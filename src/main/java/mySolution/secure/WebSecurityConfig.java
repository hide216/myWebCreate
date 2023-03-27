package mySolution.secure;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(requests -> requests
				//ログイン必須画面とその権限
				.mvcMatchers("/member/**").hasRole("USER")
				//静的リソースへのアクセス(CSS, JAVA_SCRIPT, IMAGES, WEB_JARS, FAVICON)
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
			)
			.formLogin(login -> login
				.loginPage("/login")
				//実態はProviderImplに認証を移譲
				.loginProcessingUrl("/auth")
				.defaultSuccessUrl("/member")
				.failureUrl("/login")
				//LoginPageでTokenに詰める[input name]属性
				.usernameParameter("id")
				.passwordParameter("password")
			)
			.logout(logout -> logout
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login")
				.deleteCookies("JSESSIONID")
				.invalidateHttpSession(true).permitAll()
			)
			.csrf(csrf -> csrf
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
			);
		// セッション設定
		http.sessionManagement()
			// デフォルト設定
			.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
			// ログイン前のセッションを破棄して、新しいセッションを作成する。
			// ログイン前のオブジェクトは引き継がれない（デフォルトは引き継がれる）
			.sessionFixation().newSession()
			// 同時ログイン数
			.maximumSessions(10)
			// ログインは先勝ち（false→後勝ち）
			.maxSessionsPreventsLogin(false);
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}