package com.shoponline.service;

import java.util.List;

import com.shoponline.model.Role;

public interface RoleService {

	public Role createNewRole(Role role);

    public Role saveRole(Role role);

    public Role getRoleById(int id);

    public List<Role> getAllRole();
}
