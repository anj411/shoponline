package com.shoponline.service;

import java.util.HashSet;


import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.shoponline.dao.RoleRepository;
import com.shoponline.dao.UserRepository;
import com.shoponline.model.Role;
import com.shoponline.model.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void initRoleAndUser() {

		/*
		 * Role adminRole = new Role(); adminRole.setRoleName("Admin");
		 * roleService.saveRole(adminRole);
		 * 
		 * Role userRole = new Role(); userRole.setRoleName("User");
		 * roleService.saveRole(userRole);
		 */
    	
		Role adminRole = roleService.getRoleById(1);
		
        User adminUser = new User();
        adminUser.setUserEmail("admin123@gmail.com");
        adminUser.setUserPassword(getEncodedPassword("admin@pass"));
        adminUser.setUserFirstName("admin");
        adminUser.setUserLastName("admin");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        saveUser(adminUser);
    }
    
	public User registerNewUser(User user) {
		Role role = roleService.getRoleById(2);
		Set<Role> userRoles = new HashSet<>();
		userRoles.add(role);
		user.setRole(userRoles);
		user.setUserPassword(getEncodedPassword(user.getUserPassword()));
		
		return saveUser(user);
	}
	
	public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
	
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public User getUserById(int id) {
		Optional<User> user = userRepository.findById(id);
		return user.get();
	}

	public List<User> getAllUser() {
		return userRepository.findAll();
	}
	
	public User findUserByUserName(String username) {
		return userRepository.findByUserEmail(username).get();
	}

}
