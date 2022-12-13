package com.shoponline.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoponline.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

}
