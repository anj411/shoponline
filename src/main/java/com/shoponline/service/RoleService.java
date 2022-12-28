package com.shoponline.service;

import java.util.List;

import com.shoponline.model.Role;

/**
 * This is the abstraction for Role Service
 * Operations related to Roles such as create new Role, save Role, get Role etc
 * will be called from here.
 */
public interface RoleService {

	/**
     * this service operation when called
     * will create a new role
     * @param role
     * @return
     */
	public Role createNewRole(Role role);

	/**
     * this service operation will add the passed Role object
     * and will return the same role.
     * @param role
     *@return
     */
    public Role saveRole(Role role);

    /**
     * this service operation when called returns the
     * Role corresponding to the passed roleID.
     * @param id
     * @return
     */
    public Role getRoleById(int id);

    /**
     * this service operation when called returns the
     * List of Roles.
     * @return
     */
    public List<Role> getAllRole();
}
