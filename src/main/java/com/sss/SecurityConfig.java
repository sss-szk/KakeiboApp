package com.sss;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	@Qualifier("UserDetailsServiceImpl")
	private UserDetailsService userDetailsService;
	
	//パスワードエンコーダーのBean定義
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	private DataSource dataSource;
	
	private static final String USER_SQL = "SELECT user_id ,password,enabled FROM m_user WHERE user_id = ?";

	
	@Override
	public void configure(WebSecurity web)throws Exception{
		
		//静的リソースへのアクセスには、セキュリティを適用しない
		web.ignoring().antMatchers("/webjars/**","/css/**");
	}
	
	@Override
	protected void configure(HttpSecurity http)throws Exception{
		//ログイン不要ページの設定
		http.authorizeRequests()
		.antMatchers("/webjars/**").permitAll() //webjarsへアクセス許可
		.antMatchers("/css/**").permitAll()		//cssへアクセス許可
		.antMatchers("/login").permitAll()		//ログインページは直リンクOK
		.antMatchers("/signup").permitAll()
		.antMatchers("/home").permitAll()
//		.antMatchers("/admin").hasAuthority("ROLE_ADMIN")
		.anyRequest().authenticated();			//それ以外は直リンク禁止
		
		//ログイン処理
		http.formLogin()
		.loginProcessingUrl("/login")	  	//ログイン処理のパス
		.loginPage("/login")				//ログインページの指定
		.failureUrl("/login?error")				//ログイン失敗時の遷移先
		.usernameParameter("userId")		//ログインページのユーザID
		.passwordParameter("password")		//ログインページのパスワード
		.defaultSuccessUrl("/homeLogin",true);	//ログイン成功時の遷移先
		
		//ログアウト処理
		http.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutUrl("/logout")
		.logoutSuccessUrl("/home");
		
		
		//CSRF対策を一時的に無効化
		http.csrf().disable();
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		
		//ログイン処理時のユーザー情報をDBから取得する
//		auth.jdbcAuthentication()
//		.dataSource(dataSource)
//		.usersByUsernameQuery(USER_SQL)
//		.passwordEncoder(passwordEncoder());
		
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder());
	}
	

}
