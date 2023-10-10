package com.example.springPractice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // @Beanとセットで使う
@EnableWebSecurity // SpringSecurityを使用可能にする
public class ActuatorSecurity {

	@Bean // 戻り値がSpringコンテナ上で登録・管理される
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorize -> authorize
			.requestMatchers("/item", "/item/{itemId}").permitAll() // 左記のURLは認証不要になる
			.requestMatchers("/item/add", "/item/update", "/item/delete", "/actuator/**").hasRole("ADMIN") // 左記のURLはADMINユーザのロールが必要になる
			.anyRequest().denyAll() // 上記以外はアクセス不可
			)
		.formLogin(); // ログイン画面を表示する
		return http.build();
	}

    // "/actuator/health"の認証は不要とする
    @Bean
    public WebSecurityCustomizer WebSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/actuator/health");
    }

	// actuatorの認証ユーザ
	@Bean
	public InMemoryUserDetailsManager userDitailsService() {
		UserDetails user = User.withUsername("admin")
				.password("{noop}admin") // 暗号化されていない平文のパスワードを設定する場合は"{noop}をつける"
				.roles("ADMIN")
				.build();
		return new InMemoryUserDetailsManager(user);
	}
}
