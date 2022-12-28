package com.shoponline.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoponline.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
	List<Product> findAllByCategory_Id(int categoryId);
}
