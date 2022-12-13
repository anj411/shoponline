package com.shoponline.securityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.shoponline.service.JwtServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private JwtServiceImpl jwtService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors();
        httpSecurity.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/home", "/about", "/contact-us", "/signin", "/register", "/registerNewUser").permitAll()
                .antMatchers("/men/{menuId}", "/women/{menuId}",  "/kids/{menuId}", "/home&living/{menuId}").permitAll()
                .antMatchers("/cart", "/category/{categoryId}", "/product/{productId}").permitAll()
                .antMatchers("/authenticate", "/registerNewUser").permitAll()
                .antMatchers("/forAdmin", "/admin/**").hasRole("Admin")
        		.antMatchers("/forUser", "/payment", "/address", "/placeorder").hasRole("User")
                .antMatchers(HttpHeaders.ALLOW).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/signin")
                .permitAll()
                .failureUrl("/signin?error=true")
                .defaultSuccessUrl("/home")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/home")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .deleteCookies("id-token")
                .deleteCookies("refresh-token")
				.and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
        ;

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/img/**", "/productImages/**", "/css/**", "/js/**",
				"/categoryImages/**");
	}

	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtService).passwordEncoder(passwordEncoder());
	}
	
	/*
	 * @Autowired public void configureGlobal(AuthenticationManagerBuilder
	 * authenticationManagerBuilder) throws Exception {
	 * authenticationManagerBuilder.userDetailsService(jwtService).passwordEncoder(
	 * passwordEncoder()); }
	 */
	 
}
