package com.shoponline.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shoponline.model.Menu;


public interface MenuRepository extends JpaRepository<Menu, Integer> 
{
	
}
