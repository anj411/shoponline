package com.shoponline.controller;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.shoponline.exception.CredentialValidationException;
import com.shoponline.model.JwtRequest;
import com.shoponline.model.JwtResponse;
import com.shoponline.model.User;
import com.shoponline.service.JwtServiceImpl;
import com.shoponline.service.UserService;
import com.shoponline.util.JwtCookieUtil;
import com.shoponline.util.JwtUtil;

@RestController
@CrossOrigin
public class JwtController {
	
	private Logger log = LoggerFactory.getLogger(JwtController.class.getName());
	
	@Autowired 
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtServiceImpl jwtService;
	
	@Autowired 
	private UserService userService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@RequestMapping("/signin")
	public ModelAndView login()
	{
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("login.html");
		modelView.addObject("title", "Login - Online Shopping");
		modelView.addObject("jwtRequest", new JwtRequest());
		return modelView;
	}
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {

		String userName = jwtRequest.getUserName();
		String userPassword = jwtRequest.getUserPassword();
		authenticate(userName, userPassword);

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

		try {
			UserDetails userDetails = jwtService.loadUserByUsername(userName);
			String newGeneratedToken = jwtUtil.generateToken(userDetails);
			JwtCookieUtil.addTokenCookies(attr, newGeneratedToken, "true");
			User user = userService.findUserByUserName(userName);

			Set<SimpleGrantedAuthority> authorities = (Set<SimpleGrantedAuthority>) userDetails.getAuthorities();

			for (SimpleGrantedAuthority sga : authorities) {
				if (sga.getAuthority().equals("ROLE_Admin")) {
					System.out.println("Role name in admin  :  " + sga.getAuthority());
					return new JwtResponse(user, newGeneratedToken);

				} else {
					System.out.println("Role name in user  :  " + sga.getAuthority());
					return new JwtResponse(user, newGeneratedToken);
				}
			}
			return null;

		} catch (Exception ex) {
			log.error("User = {} failed auth", userName, ex);
			JwtCookieUtil.clearTokenCookies(attr);
			throw new CredentialValidationException("incorrect credentails");
		}

		// response.addHeader("token", newGeneratedToken);
		// return ResponseEntity.ok("authenticated");
	}
	
	private void authenticate(String userName, String userPassword) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	
	/*
	 * @GetMapping("/logout") public ModelAndView logoutUser() { ModelAndView
	 * modelView = new ModelAndView(); modelView.setViewName("login.html");
	 * ServletRequestAttributes attr = (ServletRequestAttributes)
	 * RequestContextHolder.currentRequestAttributes();
	 * 
	 * JwtCookieUtil.clearTokenCookies(attr);
	 * System.out.println("Logging out .. clearing cookies."); return modelView; }
	 */

}
