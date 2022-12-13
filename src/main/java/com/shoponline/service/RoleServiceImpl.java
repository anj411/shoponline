package com.shoponline.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoponline.dao.RoleRepository;
import com.shoponline.model.Role;
import com.shoponline.model.User;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	public Role createNewRole(Role role) {
		return roleRepository.save(role);
	}
	
	public Role saveRole(Role role) {
		return roleRepository.save(role);
	}

	public Role getRoleById(int id) {
		Optional<Role> role = roleRepository.findById(id);
		return role.get();
	}

	public List<Role> getAllRole() {
		return roleRepository.findAll();
	}
}
