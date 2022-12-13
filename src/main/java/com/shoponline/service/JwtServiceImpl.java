package com.shoponline.service;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.shoponline.dao.UserRepository;
import com.shoponline.exception.CredentialValidationException;
import com.shoponline.model.JwtRequest;
import com.shoponline.model.JwtResponse;
import com.shoponline.model.User;
import com.shoponline.util.JwtCookieUtil;
import com.shoponline.util.JwtUtil;

@Service
public class JwtServiceImpl implements UserDetailsService {
	
	private Logger log = LoggerFactory.getLogger(JwtServiceImpl.class.getName());
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired 
	private UserService userService;
	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserEmail(username).get();

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUserEmail(),
                    user.getUserPassword(),
                    getAuthority(user)
            );
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        });
        return authorities;
    }
    
    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
    	String userName = jwtRequest.getUserName();
		String userPassword = jwtRequest.getUserPassword();
		authenticate(userName, userPassword);

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

		try {
			UserDetails userDetails = loadUserByUsername(userName);
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
    }
    
    /**
	 * This method when called it will perform authentication.
	 * @param userName
	 * @param userPassword
	 * @throws Exception
	 */
    private void authenticate(String userName, String userPassword) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
