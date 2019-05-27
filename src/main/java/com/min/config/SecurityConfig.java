package com.min.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.min.entity.Permission;
import com.min.mapper.PermissionMapper;
//
//import com.min.handler.MyAuthenticationFailureHandler;
//import com.min.handler.MyAuthenticationSuccessHandler;
import com.min.security.MyUserDetailService;
import com.min.utils.MD5Util;

// Security 配置
@Component
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	// @Autowired
	// private MyAuthenticationFailureHandler failureHandler;
	// @Autowired
	// private MyAuthenticationSuccessHandler successHandler;
	@Autowired
	private PermissionMapper permissionMapper;
	@Autowired
	private MyUserDetailService myUserDetailService;

	// 配置认证用户信息和权限
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//采用自定义PasswordEncoder
		auth.userDetailsService(myUserDetailService).passwordEncoder(new PasswordEncoder() {

			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				String encode = MD5Util.encode((String) rawPassword);
				System.out.println(encode);
				//123456  -- a09fea8ff3b9c3e135414f16cf06de21
				encodedPassword=encodedPassword.replace("\r\n", "");
				System.out.println(encodedPassword);
				boolean result = encodedPassword.equals(encode);
				return result;
			}

			public String encode(CharSequence rawPassword) {
				return MD5Util.encode((String) rawPassword);
			}
		});
		System.out.println(passwordEncoder().encode("123456"));
		//采用注入PasswordEncoder bean的方法（BCryptPasswordEncoder），看代码最下面
		//$2a$10$/5qaysSRNwPpjuNahi/wTeJ7nlJaV3teuC4H5KjQ9FT5sybUcr0oe
//		auth.userDetailsService(myUserDetailService).passwordEncoder(passwordEncoder());

		// // 添加admin账号
		// auth.inMemoryAuthentication().withUser("admin").password("123456").
		// authorities("showOrder","addOrder","updateOrder","deleteOrder","findUser");
		// // 添加userAdd账号
		// auth.inMemoryAuthentication().withUser("userAdd").password("123456").authorities("showOrder","addOrder");
		// // 如果想实现动态账号与数据库关联 在该地方改为查询数据库

	}

	// 配置拦截请求资源
	protected void configure(HttpSecurity http) throws Exception {
		// // 如何权限控制 给每一个请求路径 分配一个权限名称 让后账号只要关联该名称，就可以有访问权限
		// http.authorizeRequests()
		// // 配置查询订单权限
		// .antMatchers("/showOrder").hasAnyAuthority("showOrder")
		// .antMatchers("/addOrder").hasAnyAuthority("addOrder")
		// .antMatchers("/findUser").hasAnyAuthority("findUser")
		// .antMatchers("/login").permitAll()
		// .antMatchers("/updateOrder").hasAnyAuthority("updateOrder")
		// .antMatchers("/deleteOrder").hasAnyAuthority("deleteOrder")
		// .antMatchers("/**").fullyAuthenticated().and().formLogin().loginPage("/login").
		//// successHandler(successHandler).failureHandler(failureHandler)
		// and().csrf().disable();
		List<Permission> listPermission = permissionMapper.findAllPermission();
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizeRequests = http
				.authorizeRequests();
		for (Permission permission : listPermission) {
			authorizeRequests.antMatchers(permission.getUrl()).hasAnyAuthority(permission.getPermTag());
		}
		authorizeRequests.antMatchers("/login").permitAll().antMatchers("/**").fullyAuthenticated().and().formLogin()
				.loginPage("/login").and().csrf().disable();

	}

	// @Bean
	//	// public static NoOpPasswordEncoder passwordEncoder() {
	//	// return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	//	// }

	 @Bean
	 public static BCryptPasswordEncoder passwordEncoder() {
	 return new BCryptPasswordEncoder();
	 }

}
